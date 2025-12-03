package com.example.eva2.presentation.crud

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.eva2.data.remote.dto.UserDto

class CrudViewModel : ViewModel() {

    // Lista "falsa" inicial para cumplir con la evaluación
    private val _users = mutableStateListOf(
        UserDto(1, "Jorge Cortes", "jorge@mail.com"),
        UserDto(2, "Maria Gonzalez", "maria@mail.com"),
        UserDto(3, "Pedro Pascal", "pedro@mail.com")
    )

    // Lista visible (filtrada por búsqueda)
    var visibleUsers = mutableStateListOf<UserDto>()

    // Estado del buscador
    var searchQuery by mutableStateOf("")

    // Estados para el formulario flotante (Diálogo)
    var isEditing by mutableStateOf(false)
    var currentUser by mutableStateOf<UserDto?>(null) // Si es null, es nuevo. Si tiene datos, es edición.

    // Campos del formulario
    var editName by mutableStateOf("")
    var editEmail by mutableStateOf("")

    init {
        // Al inicio mostramos todos
        visibleUsers.addAll(_users)
    }

    // Función de Búsqueda
    fun search(query: String) {
        searchQuery = query
        visibleUsers.clear()
        if (query.isBlank()) {
            visibleUsers.addAll(_users)
        } else {
            visibleUsers.addAll(_users.filter {
                it.name.contains(query, ignoreCase = true)
            })
        }
    }

    // Abrir diálogo para CREAR
    fun onAddUser() {
        isEditing = true
        currentUser = null
        editName = ""
        editEmail = ""
    }

    // Abrir diálogo para EDITAR
    fun onEditUser(user: UserDto) {
        isEditing = true
        currentUser = user
        editName = user.name
        editEmail = user.email
    }

    // Guardar cambios (Crear o Editar)
    fun saveUser() {
        if (editName.isBlank() || editEmail.isBlank()) return

        if (currentUser == null) {
            // Lógica de Crear
            val newId = (_users.maxOfOrNull { it.id } ?: 0) + 1
            val newUser = UserDto(newId, editName, editEmail)
            _users.add(newUser)
        } else {
            // Lógica de Editar
            val index = _users.indexOfFirst { it.id == currentUser?.id }
            if (index != -1) {
                _users[index] = currentUser!!.copy(name = editName, email = editEmail)
            }
        }
        // Refrescar lista y cerrar
        search(searchQuery)
        isEditing = false
    }

    // Eliminar usuario
    fun deleteUser(user: UserDto) {
        _users.remove(user)
        search(searchQuery)
    }

    fun dismissDialog() {
        isEditing = false
    }
}
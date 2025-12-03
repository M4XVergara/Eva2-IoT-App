package com.example.eva2.presentation.crud

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CrudScreen(
    viewModel: CrudViewModel = viewModel(),
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Gestión de Usuarios") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { viewModel.onAddUser() }) {
                Icon(Icons.Default.Add, contentDescription = "Nuevo Usuario")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            // BARRA DE BÚSQUEDA
            OutlinedTextField(
                value = viewModel.searchQuery,
                onValueChange = { viewModel.search(it) },
                label = { Text("Buscar por nombre...") },
                leadingIcon = { Icon(Icons.Default.Search, null) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // LISTA DE USUARIOS
            LazyColumn {
                items(viewModel.visibleUsers) { user ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(user.name, style = MaterialTheme.typography.titleMedium)
                                Text(user.email, style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
                            }
                            // Botones de acción (Editar / Borrar)
                            Row {
                                IconButton(onClick = { viewModel.onEditUser(user) }) {
                                    Icon(Icons.Default.Edit, null, tint = MaterialTheme.colorScheme.primary)
                                }
                                IconButton(onClick = { viewModel.deleteUser(user) }) {
                                    Icon(Icons.Default.Delete, null, tint = MaterialTheme.colorScheme.error)
                                }
                            }
                        }
                    }
                }
            }
        }

        // DIÁLOGO EMERGENTE (Para Crear o Editar)
        if (viewModel.isEditing) {
            AlertDialog(
                onDismissRequest = { viewModel.dismissDialog() },
                title = { Text(if (viewModel.currentUser == null) "Nuevo Usuario" else "Editar Usuario") },
                text = {
                    Column {
                        OutlinedTextField(
                            value = viewModel.editName,
                            onValueChange = { viewModel.editName = it },
                            label = { Text("Nombre Completo") }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = viewModel.editEmail,
                            onValueChange = { viewModel.editEmail = it },
                            label = { Text("Correo Electrónico") }
                        )
                    }
                },
                confirmButton = {
                    Button(onClick = { viewModel.saveUser() }) {
                        Text("Guardar")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { viewModel.dismissDialog() }) {
                        Text("Cancelar")
                    }
                }
            )
        }
    }
}
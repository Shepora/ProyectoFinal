package screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import viewmodel.RoomViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewRoomsScreen(viewModel: RoomViewModel, navController: NavController) {
    val habitaciones by remember { mutableStateOf(viewModel.habitaciones) }
    var mensajeError by remember { mutableStateOf("") }

    // Obtener habitaciones al entrar
    LaunchedEffect(Unit) {
        viewModel.obtenerHabitaciones { error -> mensajeError = error }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Listado de Habitaciónes") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        content = { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (mensajeError.isNotEmpty()) {
                    item {
                        Text(mensajeError, color = Color.Red)
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }

                if (habitaciones.isEmpty()) {
                    item {
                        Text("No hay habitaciones registradas.")
                    }
                } else {
                    items(habitaciones) { habitacion ->
                        var codigo by remember { mutableStateOf(habitacion.codigo) }
                        var cantidadCamas by remember { mutableStateOf(habitacion.cantidadCamas.toString()) }
                        var estado by remember { mutableStateOf(habitacion.estado) }

                        Card(
                            shape = RoundedCornerShape(16.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier
                                    .background(Color(0xFFF6F6FE))
                                    .padding(16.dp)
                            ) {
                                OutlinedTextField(
                                    value = codigo,
                                    onValueChange = { codigo = it },
                                    label = { Text("Código") },
                                    modifier = Modifier.fillMaxWidth()
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                OutlinedTextField(
                                    value = cantidadCamas,
                                    onValueChange = { cantidadCamas = it },
                                    label = { Text("Cantidad de camas") },
                                    modifier = Modifier.fillMaxWidth()
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                OutlinedTextField(
                                    value = estado,
                                    onValueChange = { estado = it },
                                    label = { Text("Estado") },
                                    modifier = Modifier.fillMaxWidth()
                                )

                                Spacer(modifier = Modifier.height(12.dp))

                                Button(
                                    onClick = {
                                        if (codigo.isNotBlank() && cantidadCamas.isNotBlank() && estado.isNotBlank()) {
                                            viewModel.actualizarHabitacion(
                                                id = habitacion.id,
                                                codigo = codigo,
                                                cantidadCamas = cantidadCamas.toIntOrNull() ?: 0,
                                                estado = estado,
                                                onSuccess = {},
                                                onError = { mensajeError = it }
                                            )
                                        } else {
                                            mensajeError = "Todos los campos son obligatorios."
                                        }
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(48.dp),
                                    shape = RoundedCornerShape(8.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = Color(
                                        0xFF4C5AA6
                                    )
                                    )
                                ) {
                                    Text("Actualizar", color = Color.White)
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}



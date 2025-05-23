package screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import viewmodel.RoomViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRoomScreen(viewModel: RoomViewModel, navController: NavController) {
    var codigo by remember { mutableStateOf("") }
    var cantidadCamas by remember { mutableStateOf("") }
    var estado by remember { mutableStateOf("") }
    var mensajeError by remember { mutableStateOf("") }
    var mensajeExito by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Registro de Habitación") },
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
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
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
                        onValueChange = {
                            codigo = it
                            mensajeError = ""
                            mensajeExito = ""
                        },
                        label = { Text("Código") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = cantidadCamas,
                        onValueChange = {
                            cantidadCamas = it
                            mensajeError = ""
                            mensajeExito = ""
                        },
                        label = { Text("Cantidad de Camas") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = estado,
                        onValueChange = {
                            estado = it
                            mensajeError = ""
                            mensajeExito = ""
                        },
                        label = { Text("Estado") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp)
                    )

                    if (mensajeError.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = mensajeError,
                            color = Color.Red,
                            fontSize = 14.sp,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }

                    if (mensajeExito.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = mensajeExito,
                            color = Color(0xFF2E7D32), // Verde
                            fontSize = 14.sp,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            if (codigo.isNotBlank() && cantidadCamas.isNotBlank() && estado.isNotBlank()) {
                                viewModel.registrarHabitacion(
                                    codigo,
                                    cantidadCamas,
                                    estado,
                                    onSuccess = {
                                        codigo = ""
                                        cantidadCamas = ""
                                        estado = ""
                                        mensajeError = ""
                                        mensajeExito = "Habitación registrada correctamente"
                                    },
                                    onError = {
                                        mensajeError = it
                                        mensajeExito = ""
                                    }
                                )
                            } else {
                                mensajeError = "Por favor, completa todos los campos."
                                mensajeExito = ""
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4E599D))
                    ) {
                        Text("Registrar", color = Color.White)
                    }
                }
            }
        }
    }
}
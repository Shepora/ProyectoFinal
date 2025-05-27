package screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import model.ParqueaderoRequest
import viewmodel.ParqueaderoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParqueaderoRegisterScreen(
    parqueaderoViewModel: ParqueaderoViewModel,
    navController: NavHostController
) {
    val context = LocalContext.current
    var placa by remember { mutableStateOf("") }
    var codigoHabitacion by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Registrar Parqueadero") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = placa,
                onValueChange = { placa = it },
                label = { Text("Placa del vehículo") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = codigoHabitacion,
                onValueChange = { codigoHabitacion = it },
                label = { Text("Código de habitación") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    if (placa.isNotBlank() && codigoHabitacion.isNotBlank()) {
                        parqueaderoViewModel.registrarParqueadero(
                            ParqueaderoRequest(placa, codigoHabitacion),
                            onSuccess = {
                                Toast.makeText(context, "Parqueadero registrado", Toast.LENGTH_SHORT).show()
                                navController.popBackStack()
                            },
                            onError = {
                                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
                            }
                        )
                    } else {
                        Toast.makeText(context, "Por favor llena todos los campos", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Registrar")
            }
        }
    }
}

package screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import viewmodel.ReservaViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrarReservaScreen(
    reservaViewModel: ReservaViewModel,
    navController: NavHostController
) {
    val context = LocalContext.current

    var fechaIngreso by remember { mutableStateOf("") }
    var fechaSalida by remember { mutableStateOf("") }
    var codigoHabitacion by remember { mutableStateOf("") }
    var idHuesped by remember { mutableStateOf("") }
    var estado by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Registrar Reserva") },
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
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = fechaIngreso,
                onValueChange = { fechaIngreso = it },
                label = { Text("Fecha Ingreso (YYYY-MM-DD)") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = fechaSalida,
                onValueChange = { fechaSalida = it },
                label = { Text("Fecha Salida (YYYY-MM-DD)") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = codigoHabitacion,
                onValueChange = { codigoHabitacion = it },
                label = { Text("Código Habitación") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = idHuesped,
                onValueChange = { idHuesped = it },
                label = { Text("ID Huésped") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                ),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = estado,
                onValueChange = { estado = it },
                label = { Text("Estado") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    val id = idHuesped.toLongOrNull()
                    if (id != null) {
                        reservaViewModel.registrarReserva(
                            fechaIngreso,
                            fechaSalida,
                            codigoHabitacion,
                            id,
                            estado,
                            onSuccess = {
                                Toast.makeText(context, "Reserva registrada", Toast.LENGTH_LONG).show()
                                // Limpia campos
                                fechaIngreso = ""
                                fechaSalida = ""
                                codigoHabitacion = ""
                                idHuesped = ""
                                estado = ""
                            },
                            onError = { error ->
                                Toast.makeText(context, "Error: $error", Toast.LENGTH_LONG).show()
                            }
                        )
                    } else {
                        Toast.makeText(context, "ID de huésped inválido", Toast.LENGTH_LONG).show()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Registrar Reserva")
            }
        }
    }
}

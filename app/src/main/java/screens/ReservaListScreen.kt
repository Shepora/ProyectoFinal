package screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import model.ReservaResponse
import viewmodel.ReservaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReservaListScreen(
    reservaViewModel: ReservaViewModel,
    navController: NavHostController
) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        reservaViewModel.obtenerReservas(
            onSuccess = {

            },
            onError = { error ->
                Toast.makeText(context, "Error: $error", Toast.LENGTH_LONG).show()
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Lista de Reservas") },
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
        LazyColumn(
            contentPadding = padding,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(reservaViewModel.reservas.value) { reserva ->
                ReservaCard(reserva = reserva, viewModel = reservaViewModel)
            }
        }
    }
}

@Composable
fun ReservaCard(reserva: ReservaResponse, viewModel: ReservaViewModel) {
    val context = LocalContext.current
    var fechaIngreso by remember { mutableStateOf(reserva.fechaIngreso) }
    var fechaSalida by remember { mutableStateOf(reserva.fechaSalida) }
    var codigoHabitacion by remember { mutableStateOf(reserva.codigoHabitacion) }
    var idHuesped by remember { mutableStateOf(reserva.idHuesped.toString()) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text("ID: ${reserva.id}", style = MaterialTheme.typography.titleMedium)
            Text("Estado: ${reserva.estado}", style = MaterialTheme.typography.bodySmall)

            OutlinedTextField(
                value = fechaIngreso,
                onValueChange = { fechaIngreso = it },
                label = { Text("Fecha Ingreso") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = fechaSalida,
                onValueChange = { fechaSalida = it },
                label = { Text("Fecha Salida") },
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
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(onClick = {
                    val idHuespedLong = idHuesped.toLongOrNull()
                    if (idHuespedLong != null) {
                        viewModel.actualizarReserva(
                            id = reserva.id,
                            fechaIngreso = fechaIngreso,
                            fechaSalida = fechaSalida,
                            codigoHabitacion = codigoHabitacion,
                            idHuesped = idHuespedLong,
                            onSuccess = {
                                Toast.makeText(context, "Reserva actualizada", Toast.LENGTH_SHORT).show()
                            },
                            onError = {
                                Toast.makeText(context, "Error: $it", Toast.LENGTH_SHORT).show()
                            }
                        )
                    } else {
                        Toast.makeText(context, "ID Huésped inválido", Toast.LENGTH_SHORT).show()
                    }
                }) {
                    Text("Actualizar")
                }

                Button(onClick = {
                    viewModel.eliminarReserva(
                        id = reserva.id,
                        codigoHabitacion = reserva.codigoHabitacion,
                        onSuccess = {
                            Toast.makeText(context, "Reserva eliminada", Toast.LENGTH_SHORT).show()
                        },
                        onError = {
                            Toast.makeText(context, "Error: $it", Toast.LENGTH_SHORT).show()
                        }
                    )
                }) {
                    Text("Eliminar")
                }
            }
        }
    }
}

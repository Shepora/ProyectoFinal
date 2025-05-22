package screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import viewmodel.RoomViewModel

@Composable
fun AddRoomScreen(
    roomViewModel: RoomViewModel,
    navController: NavHostController
) {
    var codigo by remember { mutableStateOf("") }
    var cantidadCamas by remember { mutableStateOf("") }
    var estado by remember { mutableStateOf("") }
    var mensaje by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Agregar Habitación", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = codigo,
            onValueChange = { codigo = it },
            label = { Text("Código de habitación") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = cantidadCamas,
            onValueChange = { cantidadCamas = it },
            label = { Text("Cantidad de camas") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = estado,
            onValueChange = { estado = it },
            label = { Text("Estado (Disponible/Ocupada)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                roomViewModel.registrarHabitacion(
                    codigo, cantidadCamas, estado,
                    onSuccess = {
                        mensaje = "Habitación registrada exitosamente"
                        codigo = ""
                        cantidadCamas = ""
                        estado = ""
                    },
                    onError = {
                        mensaje = it
                    }
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar")
        }

        if (mensaje.isNotBlank()) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(mensaje, color = if (mensaje.contains("exitosamente")) Color.Green else Color.Red)
        }
    }
}

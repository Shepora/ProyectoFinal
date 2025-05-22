package viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import model.HabitacionRequest
import network.RetrofitClient

class RoomViewModel : ViewModel() {
    fun registrarHabitacion(
        codigo: String,
        cantidadCamas: String,
        estado: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val habitacion = HabitacionRequest(
                    codigo = codigo,
                    cantidadCamas = cantidadCamas.toInt(),
                    estado = estado
                )

                val response = RetrofitClient.HabitacionApiService
                    .registrarHabitacion(habitacion)
                    .execute()

                if (response.isSuccessful) {
                    onSuccess()
                } else {
                    onError("Error del servidor: ${response.code()}")
                }

            } catch (e: Exception) {
                e.printStackTrace()
                onError("Excepción: ${e.message}")
            }
        }
    }
}

package viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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

                val response = RetrofitClient.habitacionApiService
                    .registrarHabitacion(habitacion)
                    .execute()

                if (response.isSuccessful) {
                    withContext(Dispatchers.Main) {
                        onSuccess()
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        onError("Error del servidor: ${response.code()}")
                    }
                }

            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    onError("Excepción: ${e.message}")
                }
            }
        }
    }
}

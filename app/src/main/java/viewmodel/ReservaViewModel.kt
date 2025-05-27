package viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import model.ReservaConCodigoRequest
import network.RetrofitClient

class ReservaViewModel : ViewModel() {

    fun registrarReserva(
        fechaIngreso: String,
        fechaSalida: String,
        codigoHabitacion: String,
        idHuesped: Long,
        estado: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val request = ReservaConCodigoRequest(
                    fechaIngreso = fechaIngreso,
                    fechaSalida = fechaSalida,
                    codigoHabitacion = codigoHabitacion,
                    idHuesped = idHuesped,
                    estado = estado
                )

                val response = RetrofitClient.reservaApiService
                    .crearReservaConCodigo(request)
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
                withContext(Dispatchers.Main) {
                    onError("Excepción: ${e.message}")
                }
            }
        }
    }
}

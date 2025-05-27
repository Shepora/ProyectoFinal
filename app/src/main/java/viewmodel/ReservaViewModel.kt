package viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import model.ReservaConCodigoRequest
import model.ReservaResponse
import network.RetrofitClient

class ReservaViewModel : ViewModel() {

    // Lista de reservas observable para la UI
    var reservas = mutableStateOf<List<ReservaResponse>>(emptyList())
        private set

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
                    obtenerReservas({}, {}) // Refrescar lista
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

    fun obtenerReservas(
        onSuccess: (List<ReservaResponse>) -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitClient.reservaApiService.obtenerReservas().execute()
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        val reservasObtenidas = response.body() ?: emptyList()
                        reservas.value = reservasObtenidas
                        onSuccess(reservasObtenidas)
                    } else {
                        onError("Error al obtener reservas: ${response.code()}")
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    onError("Excepción: ${e.message}")
                }
            }
        }
    }

    fun actualizarReserva(
        id: Long,
        reservaActualizada: ReservaConCodigoRequest,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitClient.reservaApiService
                    .actualizarReserva(id, reservaActualizada)
                    .execute()

                if (response.isSuccessful) {
                    obtenerReservas({}, {}) // Refrescar lista
                    withContext(Dispatchers.Main) { onSuccess() }
                } else {
                    withContext(Dispatchers.Main) {
                        onError("Error al actualizar: ${response.code()}")
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    onError("Excepción: ${e.message}")
                }
            }
        }
    }

    // Versión alternativa más práctica para usar desde el panel de edición
    fun actualizarReserva(
        id: Long,
        fechaIngreso: String,
        fechaSalida: String,
        codigoHabitacion: String,
        idHuesped: Long,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        val reservaActualizada = ReservaConCodigoRequest(
            fechaIngreso = fechaIngreso,
            fechaSalida = fechaSalida,
            codigoHabitacion = codigoHabitacion,
            idHuesped = idHuesped,
            estado = "" // Se ignora el estado en edición (solo se actualiza en backend si corresponde)
        )
        actualizarReserva(id, reservaActualizada, onSuccess, onError)
    }

    fun eliminarReserva(
        id: Long,
        codigoHabitacion: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitClient.reservaApiService
                    .eliminarReserva(id)
                    .execute()

                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        // Eliminar de la lista localmente para evitar errores de renderizado
                        reservas.value = reservas.value.filterNot { it.id == id }
                        onSuccess()
                    } else {
                        onError("Error al eliminar: ${response.code()}")
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    onError("Excepción al eliminar: ${e.message}")
                }
            }
        }
    }
}
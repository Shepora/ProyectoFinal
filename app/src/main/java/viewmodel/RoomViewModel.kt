package viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import model.HabitacionRequest
import model.HabitacionResponse
import network.RetrofitClient
import androidx.compose.runtime.mutableStateListOf

class RoomViewModel : ViewModel() {

    val habitaciones = mutableStateListOf<HabitacionResponse>()

    fun registrarHabitacion(
        codigo: String,
        cantidadCamas: String,
        estado: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val habitacion = HabitacionRequest(codigo, cantidadCamas.toInt(), estado)
                val response = RetrofitClient.habitacionApiService.registrarHabitacion(habitacion).execute()
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) onSuccess()
                    else onError("Error del servidor: ${response.code()}")
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    onError("Excepción: ${e.message}")
                }
            }
        }
    }

    fun obtenerHabitaciones(onError: (String) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitClient.habitacionApiService.obtenerHabitaciones().execute()
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        habitaciones.clear()
                        response.body()?.let { habitaciones.addAll(it) }
                    } else {
                        onError("Error al obtener habitaciones: ${response.code()}")
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    onError("Excepción: ${e.message}")
                }
            }
        }
    }

    fun actualizarHabitacion(
        id: Long,
        codigo: String,
        cantidadCamas: Int,
        estado: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val habitacion = HabitacionRequest(codigo, cantidadCamas, estado)
                val response = RetrofitClient.habitacionApiService.actualizarHabitacion(id, habitacion).execute()
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        obtenerHabitaciones {}  // Refresca la lista
                        onSuccess()
                    } else {
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
}

package viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import model.ParqueaderoRequest
import network.RetrofitClient

class ParqueaderoViewModel : ViewModel() {

    fun registrarParqueadero(
        request: ParqueaderoRequest,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.parqueaderoApiService.registrarParqueadero(request)
                if (response.isSuccessful) {
                    onSuccess()
                } else {
                    onError("Error ${response.code()}: ${response.message()}")
                }
            } catch (e: Exception) {
                onError("Excepción: ${e.message}")
            }
        }
    }
}

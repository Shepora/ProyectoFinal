package viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import model.Huesped
import network.RetrofitClient

class HuespedViewModel : ViewModel() {
    fun registrarHuesped(
        huesped: Huesped,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.huespedApiService.registrarHuesped(huesped)
                if (response.isSuccessful) {
                    onSuccess()
                } else {
                    onError("Error del servidor: ${response.code()}")
                }
            } catch (e: Exception) {
                onError("Error de red: ${e.localizedMessage}")
            }
        }
    }
}
package network

import model.HabitacionRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface HabitacionApiService {
    @POST("/habitaciones")
    fun registrarHabitacion(@Body habitacion: HabitacionRequest): Call<Void>
}

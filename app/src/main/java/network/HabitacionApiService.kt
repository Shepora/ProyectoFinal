package network

import model.HabitacionRequest
import model.HabitacionResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface HabitacionApiService {
    @POST("/habitaciones")
    fun registrarHabitacion(@Body habitacion: HabitacionRequest): Call<Void>

    @GET("/habitaciones")
    fun obtenerHabitaciones(): Call<List<HabitacionResponse>>

    @PUT("/habitaciones/{id}")
    fun actualizarHabitacion(@Path("id") id: Long, @Body habitacion: HabitacionRequest): Call<Void>
}


package network

import model.Huesped
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


interface HuespedApiService {
    @POST("/huespedes")
    suspend fun registrarHuesped(@Body huesped: Huesped): Response<Huesped>
}
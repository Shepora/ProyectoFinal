package network

import model.ParqueaderoRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ParqueaderoApiService {
    @POST("/parqueaderos")
    suspend fun registrarParqueadero(@Body parqueadero: ParqueaderoRequest): Response<Void>
}
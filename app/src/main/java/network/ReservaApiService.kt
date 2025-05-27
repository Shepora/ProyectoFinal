package network

import model.ReservaConCodigoRequest
import model.ReservaResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ReservaApiService {
    @POST("/reservas/con-codigo")
    fun crearReservaConCodigo(@Body reserva: ReservaConCodigoRequest): Call<ReservaConCodigoRequest>

    @GET("/reservas")
    fun obtenerReservas(): Call<List<ReservaResponse>>

    @PUT("/reservas/{id}")
    fun actualizarReserva(@Path("id") id: Long, @Body reserva: ReservaConCodigoRequest): Call<Void>

    @DELETE("/reservas/{id}")
    fun eliminarReserva(@Path("id") id: Long): Call<Void>
}

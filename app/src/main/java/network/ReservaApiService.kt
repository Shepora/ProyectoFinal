package network

import model.ReservaConCodigoRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ReservaApiService {
    @POST("/reservas/con-codigo")
    fun crearReservaConCodigo(@Body reserva: ReservaConCodigoRequest): Call<ReservaConCodigoRequest>
}

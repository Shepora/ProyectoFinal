package model

data class ReservaConCodigoRequest(
    val fechaIngreso: String,
    val fechaSalida: String,
    val codigoHabitacion: String,
    val idHuesped: Long,
    val estado: String
)

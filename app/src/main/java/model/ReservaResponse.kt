package model

data class ReservaResponse(
    val id: Long,
    val fechaIngreso: String,
    val fechaSalida: String,
    val codigoHabitacion: String,
    val idHuesped: Long,
    val estado: String
)
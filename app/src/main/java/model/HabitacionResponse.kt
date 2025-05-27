package model

data class HabitacionResponse(
    val id: Long,
    val codigo: String,
    val cantidadCamas: Int,
    val estado: String
)

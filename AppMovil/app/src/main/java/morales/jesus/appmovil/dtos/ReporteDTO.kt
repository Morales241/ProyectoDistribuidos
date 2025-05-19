package morales.jesus.appmovil.dtos

data class ReporteDTO(
    val producto: PrecioProductoDTO,
    val contenido: String,
    val fecha: String? = null, // Se puede setear en backend
    val consumidor: Long
)
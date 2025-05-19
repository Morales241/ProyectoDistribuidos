package morales.jesus.appmovil.dtos

data class OfertaDTO(
    val comercio: String,
    val producto: String,
    val precioOferta: Double,
    val fechaInicio: String,
    val fechaFin: String,
    val descripcion: String
)

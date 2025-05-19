package morales.jesus.appmovil.dtos

data class CarritoDTO(
    val consumidor: ConsumidorDTO,
    val productos: List<CarritoProductoDTO> = emptyList()
)

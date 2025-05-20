package morales.jesus.appmovil.dtos

data class ProductoWishListDTO(
    val sugeriencia: String,
    val consumidor: Long,
    val nombreComercio: String,
    val fecha: String? = null
)
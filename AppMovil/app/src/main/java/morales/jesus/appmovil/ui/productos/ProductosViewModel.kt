package morales.jesus.appmovil.ui.productos

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import morales.jesus.appmovil.dtos.PrecioProductoDTO
import morales.jesus.appmovil.dtos.ProductoDTO
import java.net.URL

class ProductosViewModel : ViewModel() {
    private val _productos = MutableLiveData<List<ProductoDTO>>()
    val productos: LiveData<List<ProductoDTO>> = _productos

    private val _precios = MutableLiveData<List<PrecioProductoDTO>>()
    val precios: LiveData<List<PrecioProductoDTO>> = _precios

    fun cargarDatos() {
        viewModelScope.launch {
            try {
                val productosResponse = withContext(Dispatchers.IO) {
                    URL("http://192.168.0.101:8082/consumidoresComercio/buscarProductos").readText()
                }
                val preciosResponse = withContext(Dispatchers.IO) {
                    URL("http://192.168.0.101:8082/consumidoresComercio/traerPrecios").readText()
                }

                val gson = Gson()
                _productos.value = gson.fromJson(productosResponse, Array<ProductoDTO>::class.java).toList()
                _precios.value = gson.fromJson(preciosResponse, Array<PrecioProductoDTO>::class.java).toList()

            } catch (e: Exception) {
                Log.e("ViewModel", "Error al cargar productos o precios", e)
            }
        }
    }
}

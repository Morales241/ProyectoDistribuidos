package morales.jesus.appmovil.ui.preferencias

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import morales.jesus.appmovil.databinding.FragmentPreferenciasBinding
import morales.jesus.appmovil.dtos.PrecioProductoDTO
import morales.jesus.appmovil.dtos.ProductoDTO
import morales.jesus.appmovil.ui.reportes.ProductoAdapter
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONException
import java.io.IOException

class PreferenciasFragment : Fragment() {

    private lateinit var binding: FragmentPreferenciasBinding
    private lateinit var productosAdapter: ProductoAdapter
    private lateinit var preferenciasAdapter: PreferenciaAdapter
    private lateinit var carritoAdapter: CarritoAdapter

    private var consumidorId: String? = null
    private val productos = mutableListOf<ProductoDTO>()
    private var productoSeleccionado: ProductoDTO? = null
    private var supermercadoSeleccionado: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPreferenciasBinding.inflate(inflater, container, false)
        consumidorId = obtenerConsumidorIdDesdePreferencias()

        setupRecyclerViews()
        setupBusquedas()
        cargarDatosIniciales()

        return binding.root
    }

    private fun obtenerConsumidorIdDesdePreferencias(): String? {
        val prefs = requireContext().getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        return prefs.getString("consumidorId", null)
    }

    private fun setupRecyclerViews() {
        productosAdapter = ProductoAdapter { producto ->
            productoSeleccionado = producto
        }
        preferenciasAdapter = PreferenciaAdapter()
        carritoAdapter = CarritoAdapter()

        binding.sugerenciasRecyclerView.adapter = productosAdapter
        binding.preferenciasRecyclerView.adapter = preferenciasAdapter
        binding.carritoRecyclerView.adapter = carritoAdapter
    }

    private fun setupBusquedas() {
        binding.buscarProductoEditText.setOnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN) {
                val query = binding.buscarProductoEditText.text.toString()
                buscarProducto(query)
                true
            } else false
        }

        binding.buscarComercioEditText.setOnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN) {
                val query = binding.buscarComercioEditText.text.toString()
                buscarSupermercado(query)
                true
            } else false
        }
    }

    private fun cargarDatosIniciales() {
        obtenerProductos()
        obtenerCarrito()
        obtenerPreferencias()
    }

    private fun obtenerProductos() {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("http://192.168.0.101:8082/consumidoresComercio/buscarProductos")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                activity?.runOnUiThread {
                    Toast.makeText(requireContext(), "Error al obtener productos: ${e.message}", Toast.LENGTH_SHORT).show()
                    Log.e("Preferencias", "Error de red", e)
                }
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    response.body?.string()?.let { bodyStr ->
                        try {
                            val jsonArray = JSONArray(bodyStr)
                            val productos = mutableListOf<PrecioProductoDTO>()

                            for (i in 0 until jsonArray.length()) {
                                val obj = jsonArray.getJSONObject(i)
                                val comercio = obj.getString("comercio")
                                val producto = obj.getString("producto")
                                val precio = obj.getDouble("precio")

                                productos.add(PrecioProductoDTO(comercio, producto, precio))
                            }

                            activity?.runOnUiThread {
                                productosAdapter.setData(productos)
                            }
                        } catch (e: JSONException) {
                            Log.e("Preferencias", "Error parseando JSON", e)
                        }
                    }
                } else {
                    activity?.runOnUiThread {
                        Toast.makeText(requireContext(), "Error: ${response.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }


    private fun obtenerCarrito() { /* llamada HTTP y setCarrito */ }

    private fun obtenerPreferencias() { /* llamada HTTP y setPreferencias */ }

    private fun buscarProducto(query: String) { /* filtra productos y actualiza adapter */ }

    private fun buscarSupermercado(query: String) { /* consulta HTTP y muestra resultados */ }

    private fun guardarPreferencia() { /* POST a backend */ }

}

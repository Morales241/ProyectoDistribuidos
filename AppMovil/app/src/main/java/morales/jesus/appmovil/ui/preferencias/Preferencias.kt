package morales.jesus.appmovil.ui.preferencias

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import morales.jesus.appmovil.R
import morales.jesus.appmovil.databinding.FragmentPreferenciasBinding
import morales.jesus.appmovil.dtos.CarritoDTO
import morales.jesus.appmovil.dtos.CarritoProductoDTO
import morales.jesus.appmovil.dtos.PrecioProductoDTO
import morales.jesus.appmovil.dtos.PreferenciasDTO
import morales.jesus.appmovil.dtos.ProductoDTO
import morales.jesus.appmovil.ui.reportes.ProductoAdapter
import okhttp3.Call
import okhttp3.Callback
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class PreferenciasFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerView2: RecyclerView
    private val listaPreferencias = mutableListOf<PreferenciasDTO>()
    private val listaProductos = mutableListOf<CarritoProductoDTO>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_preferencias, container, false)



        val prefs = requireContext().getSharedPreferences("datos_usuario", MODE_PRIVATE)
        val consumidorId = prefs.getInt("consumidorId", -1)

        if (consumidorId != -1) {
            obtenerProductos()
            obtenerCarrito(consumidorId)
            obtenerPreferencias(consumidorId)
        } else {
            Toast.makeText(requireContext(), "Usuario no autenticado", Toast.LENGTH_SHORT).show()
        }


        recyclerView = view.findViewById(R.id.recyclerPreferencias)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = PreferenciasAdapter(listaPreferencias)

        recyclerView2 = view.findViewById(R.id.recyclerCarrito)
        recyclerView2.layoutManager = LinearLayoutManager(requireContext())
        recyclerView2.adapter = CarritoAdapter(listaProductos)


        val inputProducto = view.findViewById<android.widget.EditText>(R.id.inputBusquedaProducto)
        val inputSupermercado = view.findViewById<android.widget.EditText>(R.id.inputBusquedaSupermercado)
        val btnGuardar = view.findViewById<android.widget.Button>(R.id.btnGuardarPreferencias)
        val btnVolver = view.findViewById<android.widget.Button>(R.id.btnVolver)

        btnGuardar.setOnClickListener {
            val producto = inputProducto.text.toString().trim()
            val supermercado = inputSupermercado.text.toString().trim()

            if (producto.isEmpty() || supermercado.isEmpty()) {
                Toast.makeText(requireContext(), "Debe ingresar un producto y un supermercado", Toast.LENGTH_SHORT).show()
            } else {
                guardarPreferencias(consumidorId, supermercado, producto)
                inputProducto.text.clear()
                inputSupermercado.text.clear()
                Toast.makeText(requireContext(), "Preferencia guardada", Toast.LENGTH_SHORT).show()
            }
        }

        btnVolver.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        return view
    }

    private fun obtenerProductos() {
        val request = Request.Builder()
            .url("http://192.168.0.101:8766/DOMINIOCONSUMIDOR/consumidoresComercio/buscarProductos")
            .build()

        OkHttpClient().newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("productos", "Error al obtener productos", e)
            }

            override fun onResponse(call: Call, response: Response) {
                response.body?.string()?.let {
                    val productosJson = JSONArray(it)
                    val productosList = mutableListOf<String>()
                    for (i in 0 until productosJson.length()) {
                        val nombre = productosJson.getJSONObject(i).getString("nombre")
                        productosList.add(nombre)
                    }
                    activity?.runOnUiThread {

                    }
                }
            }
        })
    }

    private fun obtenerCarrito(idConsumidor: Int) {
        val request = Request.Builder()
            .url("http://192.168.0.101:8766/DOMINIOCONSUMIDOR/carritos/obtenerCarrito/$idConsumidor")
            .build()

        OkHttpClient().newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("carrito", "Error al obtener carrito", e)
            }

            override fun onResponse(call: Call, response: Response) {
                response.body?.string()?.let {
                    val carrito = Gson().fromJson(it, CarritoDTO::class.java)
                    activity?.runOnUiThread {
                        // actualizar UI con carrito
                    }
                }
            }
        })
    }

    private fun obtenerPreferencias(idConsumidor: Int) {
        val request = Request.Builder()
            .url("http://192.168.0.101:8766/DOMINIOCONSUMIDOR/preferencias/obtenerPreferencias/$idConsumidor")
            .build()

        OkHttpClient().newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("preferencias", "Error", e)
            }

            override fun onResponse(call: Call, response: Response) {
                response.body?.string()?.let {
                    val json = response.body?.string()
                    Log.d("preferencias", "JSON recibido: $json")

                    val prefs = Gson().fromJson(json, Array<PreferenciasDTO>::class.java).toList()
                    activity?.runOnUiThread {
                        // actualizar UI con prefs
                    }
                }
            }
        })
    }

    private fun buscarSupermercado(nombre: String, callback: (String?) -> Unit) {
        val url = "http://192.168.0.101:8766/DOMINIOCONSUMIDOR/consumidoresComercio/buscarComercioPorNombre?nombre=$nombre"
        val request = Request.Builder().url(url).build()

        OkHttpClient().newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback(null)
            }

            override fun onResponse(call: Call, response: Response) {
                val comercio = JSONObject(response.body?.string() ?: "").optString("nombre", null)
                callback(comercio)
            }
        })
    }

    private fun guardarPreferencias(idConsumidor: Int, supermercado: String, producto: String) {
        val url = "http://192.168.0.101:8766/DOMINIOCONSUMIDOR/preferencias/agregarPreferencia/$idConsumidor/$supermercado/$producto"
        val request = Request.Builder().url(url).post(FormBody.Builder().build()).build()

        OkHttpClient().newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("preferencias", "Error al guardar", e)
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    activity?.runOnUiThread {
                        obtenerPreferencias(idConsumidor)
                    }
                }
            }
        })
    }


}

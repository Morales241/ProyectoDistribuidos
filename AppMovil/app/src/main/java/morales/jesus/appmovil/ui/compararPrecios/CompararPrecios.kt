package morales.jesus.appmovil.ui.compararPrecios

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import morales.jesus.appmovil.R
import morales.jesus.appmovil.dtos.PrecioProductoDTO
import morales.jesus.appmovil.dtos.ProductoDTO
import morales.jesus.appmovil.ui.productos.ProductosViewModel
import org.json.JSONObject

class CompararPrecios : Fragment() {
    private lateinit var viewModel: ProductosViewModel
    private lateinit var producto: ProductoDTO
    private lateinit var preciosProducto: List<PrecioProductoDTO>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_comparar_precios, container, false)
        val nombreProducto = arguments?.getString("nombreProducto") ?: ""


        viewModel = ViewModelProvider(requireActivity())[ProductosViewModel::class.java]

        viewModel.productos.observe(viewLifecycleOwner) { productos ->
            producto = productos.first { it.nombre == nombreProducto }
            view.findViewById<TextView>(R.id.textTituloProducto).text = producto.nombre
            view.findViewById<TextView>(R.id.textDescripcion).text =
                "Descripción: ${producto.descripcion}"
            view.findViewById<TextView>(R.id.textCategoria).text =
                "Categoría: ${producto.categoria}"
        }

        viewModel.precios.observe(viewLifecycleOwner) { precios ->
            preciosProducto = precios.filter { it.producto == nombreProducto }
        }

        val inputTienda = view.findViewById<EditText>(R.id.inputTienda)
        val textPrecio = view.findViewById<TextView>(R.id.textPrecioTienda)
        view.findViewById<Button>(R.id.btnBuscarPrecio).setOnClickListener {
            val tienda = inputTienda.text.toString()
            val precio = preciosProducto.find { it.comercio.equals(tienda, ignoreCase = true) }
            textPrecio.text = precio?.let { "Precio: $${it.precio}" } ?: "No encontrado"
        }

        val inputTiendaA = view.findViewById<EditText>(R.id.inputTiendaA)
        val inputTiendaB = view.findViewById<EditText>(R.id.inputTiendaB)
        val textComparacion = view.findViewById<TextView>(R.id.textResultadoComparacion)

        view.findViewById<Button>(R.id.btnComparar).setOnClickListener {
            val tiendaA = inputTiendaA.text.toString()
            val tiendaB = inputTiendaB.text.toString()
            val precioA =
                preciosProducto.find { it.comercio.equals(tiendaA, ignoreCase = true) }?.precio
            val precioB =
                preciosProducto.find { it.comercio.equals(tiendaB, ignoreCase = true) }?.precio

            textComparacion.text = when {
                precioA == null || precioB == null -> "No se encontraron precios"
                precioA < precioB -> "Más barato en $tiendaA"
                precioB < precioA -> "Más barato en $tiendaB"
                else -> "Precios iguales"
            }
        }

        view.findViewById<Button>(R.id.btnVolver).setOnClickListener {
            findNavController().navigateUp()
        }

        val btnAgregarCarrito = view.findViewById<Button>(R.id.btnAgregarCarrito)
        val layoutCantidad = view.findViewById<LinearLayout>(R.id.layoutCantidad)
        val inputCantidad = view.findViewById<EditText>(R.id.inputCantidad)
        val btnConfirmarCantidad = view.findViewById<Button>(R.id.btnConfirmarCantidad)

        view.findViewById<Button>(R.id.btnBuscarPrecio).setOnClickListener {
            val tienda = inputTienda.text.toString()
            val precio = preciosProducto.find { it.comercio.equals(tienda, ignoreCase = true) }

            if (precio != null) {
                textPrecio.text = "Precio: $${precio.precio}"
                btnAgregarCarrito.visibility = View.VISIBLE

                btnAgregarCarrito.setOnClickListener {
                    layoutCantidad.visibility = View.VISIBLE
                }

                btnConfirmarCantidad.setOnClickListener {
                    val cantidad = inputCantidad.text.toString().toIntOrNull()
                    if (cantidad != null && cantidad in 1..15) {
                        agregarProductoAlCarrito(precio, cantidad)
                        Toast.makeText(context, "Producto agregado", Toast.LENGTH_SHORT).show()
                        layoutCantidad.visibility = View.GONE
                        btnAgregarCarrito.visibility = View.GONE
                    } else {
                        Toast.makeText(context, "Cantidad inválida", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                textPrecio.text = "No encontrado"
                btnAgregarCarrito.visibility = View.GONE
                layoutCantidad.visibility = View.GONE
            }
        }
        return view
    }

    fun agregarProductoAlCarrito(precioProducto: PrecioProductoDTO, cantidad: Int) {
        val prefs = requireContext().getSharedPreferences("datos_usuario", MODE_PRIVATE)
        val consumidorId = prefs.getInt("consumidorId", -1)

        val productoJson = JSONObject().apply {
            put("producto", JSONObject().apply {
                put("producto", precioProducto.producto)
                put("comercio", precioProducto.comercio)
                put("precio", precioProducto.precio)
            })
            put("cantidad", cantidad)
        }

        val url = "http://192.168.0.101:8766/DOMINIOCONSUMIDOR/carritos/agregarACarrito/$consumidorId"
        val requestQueue = Volley.newRequestQueue(requireContext())

        val request = JsonObjectRequest(
            Request.Method.POST, url, productoJson,
            { response ->
                Log.d("Carrito", "Agregado correctamente: $response")
            },
            { error ->
                Log.e("Carrito", "Error al agregar: ${error.message}")
            }
        )

        requestQueue.add(request)
    }

}

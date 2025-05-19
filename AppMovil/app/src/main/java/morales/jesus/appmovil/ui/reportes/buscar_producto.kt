package morales.jesus.appmovil.ui.reportes

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import morales.jesus.appmovil.R

class buscar_producto : Fragment() {

    private lateinit var supermercado: String
    private lateinit var etBusquedaProducto: EditText
    private lateinit var rvResultadosProducto: RecyclerView
    private lateinit var adaptador: ProductoAdapter
    private val productos = mutableListOf<String>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val vista = inflater.inflate(R.layout.fragment_buscar_producto, container, false)
        supermercado = arguments?.getString("supermercado") ?: ""
        etBusquedaProducto = vista.findViewById(R.id.etBusquedaProducto)
        rvResultadosProducto = vista.findViewById(R.id.rvResultadosProducto)

        adaptador = ProductoAdapter(productos) { seleccionado ->
            val bundle = Bundle().apply {
                putString("supermercado", supermercado)
                putString("producto", seleccionado)
            }
            findNavController().navigate(R.id.action_buscarProducto_to_agregarComentario, bundle)
        }
        rvResultadosProducto.layoutManager = LinearLayoutManager(requireContext())
        rvResultadosProducto.adapter = adaptador

        etBusquedaProducto.setOnEditorActionListener { _, actionId, _  ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                Log.e("reporte buscar producto", "entro al metodo del observer")
                buscarProducto()
                true
            } else{
                false
            }
        }

        vista.findViewById<Button>(R.id.btnVolver).setOnClickListener {
            findNavController().popBackStack()
        }

        return vista
    }

    private fun buscarProducto() {
        val termino = etBusquedaProducto.text.toString().lowercase()
        val url = "http://192.168.0.101:8082/consumidoresComercio/traerPrecios"
        val queue = Volley.newRequestQueue(requireContext())
        val request = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response ->
                productos.clear()
                for (i in 0 until response.length()) {
                    val obj = response.getJSONObject(i)
                    if (obj.getString("comercio") == supermercado &&
                        obj.getString("producto").lowercase().contains(termino)
                    ) {
                        productos.add(obj.getString("producto"))
                    }
                }
                adaptador.notifyDataSetChanged()
            },
            { error -> Toast.makeText(requireContext(), "Error: ${error.message}", Toast.LENGTH_SHORT).show() }
        )
        queue.add(request)
    }
}
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
import androidx.privacysandbox.tools.core.model.Method
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import morales.jesus.appmovil.R

class buscar_supermercado : Fragment() {
    private lateinit var etBusquedaSuper: EditText
    private lateinit var rvResultadosSuper: RecyclerView
    private lateinit var btnVolver: Button
    private lateinit var adaptador: SupermercadoAdapter
    private var resultados = mutableListOf<String>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val vista = inflater.inflate(R.layout.fragment_buscar_supermercado, container, false)
        etBusquedaSuper = vista.findViewById(R.id.etBusquedaSuper)
        rvResultadosSuper = vista.findViewById(R.id.rvResultadosSuper)

        adaptador = SupermercadoAdapter(resultados) { seleccionado ->
            val bundle = Bundle().apply { putString("supermercado", seleccionado) }
            findNavController().navigate(R.id.action_buscarSupermercado_to_buscarProducto, bundle)
        }
        rvResultadosSuper.layoutManager = LinearLayoutManager(requireContext())
        rvResultadosSuper.adapter = adaptador

        etBusquedaSuper.setOnEditorActionListener { _, actionId, _  ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                Log.e("reporte buscar comercio", "entro al metodo del observer")
                buscarSupermercado(etBusquedaSuper.text.toString())
                true
            } else{
                false
            }
        }
        return vista
    }

    private fun buscarSupermercado(nombre:String) {
        Log.e("reporte buscar comercio", "entro al metodo de buscar comercio")
        val url = "http://192.168.0.101:8082/consumidoresComercio/buscarComercioPorNombre?nombre=$nombre"
        val queue = Volley.newRequestQueue(requireContext())
        val request = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->
                val nombre = response.getString("nombre")
                resultados.clear()
                resultados.add(nombre)
                adaptador.notifyDataSetChanged()
            },
            { error -> Toast.makeText(requireContext(), "Error: ${error.message}", Toast.LENGTH_SHORT).show() }
        )
        queue.add(request)
    }
}
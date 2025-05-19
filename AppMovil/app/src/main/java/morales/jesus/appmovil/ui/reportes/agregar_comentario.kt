package morales.jesus.appmovil.ui.reportes

import android.content.Context
import android.content.Context.MODE_PRIVATE
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import morales.jesus.appmovil.R
import org.json.JSONObject

class agregar_comentario : Fragment() {
    private lateinit var supermercado: String
    private lateinit var producto: String
    private lateinit var etComentario: EditText

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val vista = inflater.inflate(R.layout.fragment_agregar_comentario, container, false)

        supermercado = arguments?.getString("supermercado") ?: ""
        producto = arguments?.getString("producto") ?: ""
        etComentario = vista.findViewById(R.id.etComentario)

        vista.findViewById<TextView>(R.id.tvResumen).text =
            "Supermercado: $supermercado\nProducto: $producto"

        vista.findViewById<Button>(R.id.btnConfirmar).setOnClickListener { enviarReporte() }
        vista.findViewById<Button>(R.id.btnCancelar).setOnClickListener {
            findNavController().popBackStack(R.id.navigation_Reportes, false)
        }

        return vista
    }

    private fun enviarReporte() {
        val comentario = etComentario.text.toString()
        val prefs = requireContext().getSharedPreferences("datos_usuario", MODE_PRIVATE)
        val consumidorId = prefs.getInt("consumidorId", -1)

        val json = JSONObject().apply {
            put("producto", JSONObject().apply {
                put("comercio", supermercado)
                put("producto", producto)
                put("precio", JSONObject.NULL)
            })
            put("contenido", comentario)
            put("fecha", JSONObject.NULL)
            put("consumidor", consumidorId)
        }

        val request = JsonObjectRequest(
            Request.Method.POST,
            "http://192.168.0.101:8082/reportes/agregar", json,
            {
                Toast.makeText(requireContext(), "¡Reporte enviado!", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack(R.id.navigation_Reportes, false)
            },
            { error -> Toast.makeText(requireContext(), "Error al enviar: ${error.message}", Toast.LENGTH_SHORT).show() }
        )

        Volley.newRequestQueue(requireContext()).add(request)
    }
}
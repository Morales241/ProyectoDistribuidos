package morales.jesus.appmovil.ui.reseñas

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import morales.jesus.appmovil.R
import morales.jesus.appmovil.dtos.ComercioDTO
import morales.jesus.appmovil.dtos.ResenaDTO
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import java.io.IOException

class Resenas : Fragment() {

    private lateinit var spinnerComercios: Spinner
    private lateinit var editTextContenido: EditText
    private lateinit var ratingBar: RatingBar
    private lateinit var btnEnviar: Button
    private lateinit var btnCancelar: Button

    private var comercios: List<ComercioDTO> = emptyList()
    private var comercioSeleccionado: ComercioDTO? = null
    private var consumidorID: Int = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_resenas, container, false)

        val prefs = requireContext().getSharedPreferences("datos_usuario", MODE_PRIVATE)
        consumidorID = prefs.getInt("consumidorId", -1)

        spinnerComercios = view.findViewById(R.id.spinnerComercios)
        editTextContenido = view.findViewById(R.id.etContenido)
        ratingBar = view.findViewById(R.id.ratingBar)
        btnEnviar = view.findViewById(R.id.btnEnviarResena)
        btnCancelar = view.findViewById(R.id.btnCancelar)

        btnEnviar.setOnClickListener { confirmarResena() }
        btnCancelar.setOnClickListener { limpiarCampos() }

        obtenerComercios()

        return view
    }

    private fun obtenerComercios() {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("http://192.168.0.101:8766/DOMINIOCONSUMIDOR/consumidoresComercio/traerComercios")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                if (isAdded) {
                    Log.e("error", "error al cargar comercios: ${e.message}")
                }
            }

            override fun onResponse(call: Call, response: Response) {
                response.body?.string()?.let {
                    val gson = Gson()
                    comercios = gson.fromJson(it, Array<ComercioDTO>::class.java).toList()
                    Log.e("comercios", "${comercios.toString()}")
                    if (isAdded) {
                        requireActivity().runOnUiThread {
                            val adapter = ArrayAdapter(
                                requireContext(),
                                android.R.layout.simple_spinner_item,
                                comercios.map { comercio -> comercio.nombre }
                            )
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                            spinnerComercios.adapter = adapter

                            spinnerComercios.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                                override fun onItemSelected(
                                    parent: AdapterView<*>, view: View?, position: Int, id: Long
                                ) {
                                    comercioSeleccionado = comercios[position]
                                }

                                override fun onNothingSelected(parent: AdapterView<*>) {
                                    comercioSeleccionado = null
                                }
                            }
                        }
                    }
                }
            }
        })
    }

    private fun confirmarResena() {
        val contenido = editTextContenido.text.toString()
        val calificacion = ratingBar.rating.toInt()

        if (comercioSeleccionado == null || contenido.isBlank()) {
            Toast.makeText(requireContext(), "Completa todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        val resena = ResenaDTO(
            contenido,
            comercioSeleccionado!!.nombre,
            consumidorID.toLong(),
            calificacion
        )

        val gson = Gson()
        val body = RequestBody.create(
            "application/json".toMediaType(),
            gson.toJson(resena)
        )

        val request = Request.Builder()
            .url("http://192.168.0.101:8766/DOMINIOCONSUMIDOR/resenas/guardarResena")
            .post(body)
            .build()

        OkHttpClient().newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                if (isAdded) {
                    requireActivity().runOnUiThread {
                        Toast.makeText(requireContext(), "Error al enviar reseña", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onResponse(call: Call, response: Response) {
                if (isAdded) {
                    requireActivity().runOnUiThread {
                        Toast.makeText(requireContext(), "¡Reseña enviada!", Toast.LENGTH_SHORT).show()
                        limpiarCampos()
                    }
                }
            }
        })
    }


    private fun limpiarCampos() {
        editTextContenido.setText("")
        ratingBar.rating = 1f
        spinnerComercios.setSelection(0)
        requireActivity().onBackPressedDispatcher.onBackPressed()
    }
}

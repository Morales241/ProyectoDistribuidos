package morales.jesus.appmovil.ui.wishlist

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import morales.jesus.appmovil.R
import morales.jesus.appmovil.dtos.ComercioDTO
import morales.jesus.appmovil.dtos.ProductoWishListDTO
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import java.io.IOException

class Wishlist : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var tvSeleccionado: TextView
    private lateinit var tvTipo: TextView
    private lateinit var layoutDetalle: LinearLayout
    private lateinit var etSugerencia: EditText
    private lateinit var btnEnviar: Button
    private lateinit var btnCancelar: Button

    private var comercios = listOf<ComercioDTO>()
    private var comercioSeleccionado: ComercioDTO? = null
    private var consumidorID = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_wishlist, container, false)

        recyclerView = view.findViewById(R.id.rvComercios)
        tvSeleccionado = view.findViewById(R.id.tvComercioSeleccionado)
        tvTipo = view.findViewById(R.id.tvTipoComercio)
        layoutDetalle = view.findViewById(R.id.layoutDetalleComercio)
        etSugerencia = view.findViewById(R.id.etSugerencia)
        btnEnviar = view.findViewById(R.id.btnEnviar)
        btnCancelar = view.findViewById(R.id.btnCancelar)

        val prefs = requireContext().getSharedPreferences("datos_usuario", MODE_PRIVATE)
        consumidorID = prefs.getInt("consumidorId", -1)

        btnEnviar.setOnClickListener { enviarSugerencia() }
        btnCancelar.setOnClickListener { limpiar() }

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        obtenerComercios()

        return view
    }

    private fun obtenerComercios() {
        val request = Request.Builder()
            .url("http://192.168.0.101:8766/DOMINIOCONSUMIDOR/consumidoresComercio/traerComercios")
            .build()

        OkHttpClient().newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                requireActivity().runOnUiThread {
                    Toast.makeText(requireContext(), "Error cargando comercios", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                response.body?.string()?.let {
                    val gson = Gson()
                    comercios = gson.fromJson(it, Array<ComercioDTO>::class.java).toList()

                    requireActivity().runOnUiThread {
                        recyclerView.adapter = ComercioAdapterWL(comercios) { comercio ->
                            comercioSeleccionado = comercio
                            layoutDetalle.visibility = View.VISIBLE
                            tvSeleccionado.text = "Comercio seleccionado: ${comercio.nombre}"
                            tvTipo.text = "Tipo: ${comercio.tipo}"
                        }
                    }
                }
            }
        })
    }

    private fun enviarSugerencia() {
        val sugerencia = etSugerencia.text.toString()
        if (sugerencia.isBlank() || comercioSeleccionado == null) {
            Toast.makeText(requireContext(), "Completa todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        val dto = ProductoWishListDTO(
            sugeriencia = sugerencia,
            consumidor = consumidorID.toLong(),
            nombreComercio = comercioSeleccionado!!.nombre
        )

        val json = Gson().toJson(dto)
        val body = RequestBody.create("application/json".toMediaType(), json)

        val request = Request.Builder()
            .url("http://192.168.0.101:8766/DOMINIOCONSUMIDOR/wishList/guardarWishList")
            .post(body)
            .build()

        OkHttpClient().newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                requireActivity().runOnUiThread {
                    Toast.makeText(requireContext(), "Error al enviar sugerencia", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                requireActivity().runOnUiThread {
                    Toast.makeText(requireContext(), "¡Sugerencia enviada!", Toast.LENGTH_SHORT).show()
                    limpiar()
                }
            }
        })
    }

    private fun limpiar() {
        comercioSeleccionado = null
        layoutDetalle.visibility = View.GONE
        etSugerencia.text.clear()
    }
}
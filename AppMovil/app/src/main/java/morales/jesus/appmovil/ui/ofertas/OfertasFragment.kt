package morales.jesus.appmovil.ui.ofertas

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import morales.jesus.appmovil.R
import morales.jesus.appmovil.dtos.OfertaDTO
import java.net.URL

class OfertasFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: OfertaAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_ofertas, container, false)
        recyclerView = view.findViewById(R.id.rvOfertas)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        obtenerOfertas()

        val btnVolver:Button = view.findViewById(R.id.btnVolver)

        btnVolver.setOnClickListener {
            findNavController().navigate(R.id.navigation_home)
        }

        return view
    }

    private fun obtenerOfertas() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val json = URL("http://192.168.0.101:8082/notificaciones/verOfertas").readText()
                val gson = Gson()
                val lista = gson.fromJson(json, Array<OfertaDTO>::class.java).toList()

                withContext(Dispatchers.Main) {
                    adapter = OfertaAdapter(lista)
                    recyclerView.adapter = adapter
                }
            } catch (e: Exception) {
                Log.e("OfertasFragment", "Error al obtener ofertas", e)
            }
        }
    }
}

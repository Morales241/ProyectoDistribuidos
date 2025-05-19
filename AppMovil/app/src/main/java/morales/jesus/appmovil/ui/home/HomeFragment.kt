package morales.jesus.appmovil.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import morales.jesus.appmovil.R
import morales.jesus.appmovil.databinding.FragmentHomeBinding
import morales.jesus.appmovil.ui.productos.ProductosViewModel

class HomeFragment : Fragment() {
    private lateinit var viewModel: ProductosViewModel
    private lateinit var gridLayout: GridLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        gridLayout = view.findViewById(R.id.gridProductos)

        viewModel = ViewModelProvider(requireActivity())[ProductosViewModel::class.java]

        viewModel.productos.observe(viewLifecycleOwner) { productos ->
            gridLayout.removeAllViews()
            productos.forEach { producto ->
                val itemView = layoutInflater.inflate(R.layout.itemproducto, gridLayout, false)

                itemView.findViewById<TextView>(R.id.tvNombreProducto).text = producto.nombre
                itemView.findViewById<TextView>(R.id.tvCategoria).text = producto.categoria

                itemView.setOnClickListener {
                    val bundle = Bundle().apply {
                        putString("nombreProducto", producto.nombre)
                    }
                    findNavController().navigate(R.id.compararPrecios, bundle)

                }

                gridLayout.addView(itemView)
            }
        }
        val btnVerOfertas = view.findViewById<Button>(R.id.btnVerOfertas)
        val btnSalir = view.findViewById<Button>(R.id.btnSalir)

        btnVerOfertas.setOnClickListener {
            findNavController().navigate(R.id.ofertasFragment)
        }

        btnSalir.setOnClickListener {
            requireActivity().finish()
        }


        viewModel.cargarDatos()
        return view
    }
}

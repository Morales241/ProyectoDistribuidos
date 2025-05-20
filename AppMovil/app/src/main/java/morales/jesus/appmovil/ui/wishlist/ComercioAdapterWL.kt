package morales.jesus.appmovil.ui.wishlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import morales.jesus.appmovil.R
import morales.jesus.appmovil.dtos.ComercioDTO

class ComercioAdapterWL(
    private val comercios: List<ComercioDTO>,
    private val onClick: (ComercioDTO) -> Unit
) : RecyclerView.Adapter<ComercioAdapterWL.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nombre: TextView = view.findViewById(R.id.tvItemSupermercado)

        init {
            view.setOnClickListener {
                onClick(comercios[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_supermercado, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = comercios.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nombre.text = comercios[position].nombre
    }
}

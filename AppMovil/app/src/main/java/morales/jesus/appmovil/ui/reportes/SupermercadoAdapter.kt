package morales.jesus.appmovil.ui.reportes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import morales.jesus.appmovil.R

class SupermercadoAdapter(
    private val supermercados: List<String>,
    private val onClick: (String) -> Unit
) : RecyclerView.Adapter<SupermercadoAdapter.SupermercadoViewHolder>() {

    inner class SupermercadoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvSupermercado: TextView = itemView.findViewById(R.id.tvItemSupermercado)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SupermercadoViewHolder {
        val vista = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_supermercado, parent, false)
        return SupermercadoViewHolder(vista)
    }

    override fun onBindViewHolder(holder: SupermercadoViewHolder, position: Int) {
        val supermercado = supermercados[position]
        holder.tvSupermercado.text = supermercado
        holder.itemView.setOnClickListener { onClick(supermercado) }
    }

    override fun getItemCount(): Int = supermercados.size
}

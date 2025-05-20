package morales.jesus.appmovil.ui.preferencias

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import morales.jesus.appmovil.R
import morales.jesus.appmovil.dtos.CarritoProductoDTO
import morales.jesus.appmovil.dtos.PreferenciasDTO

class PreferenciasAdapter(
    private val lista: List<PreferenciasDTO>
) : RecyclerView.Adapter<PreferenciasAdapter.PreferenciasViewHolder>() {

    inner class PreferenciasViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtProductoNombre: TextView = itemView.findViewById(R.id.preferenciaProducto)
        val txtComercio: TextView = itemView.findViewById(R.id.preferenciaComercio)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PreferenciasViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_preferencia, parent, false)
        return PreferenciasViewHolder(view)
    }

    override fun onBindViewHolder(holder: PreferenciasViewHolder, position: Int) {
        val item = lista[position]
        holder.txtProductoNombre.text = item.producto
        holder.txtComercio.text = "${item.comercio}"
    }

    override fun getItemCount(): Int = lista.size
}


package morales.jesus.appmovil.ui.reportes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import morales.jesus.appmovil.R

class ProductoAdapter(
    private val productos: List<String>,
    private val onClick: (String) -> Unit
) : RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder>() {

    inner class ProductoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvProducto: TextView = itemView.findViewById(R.id.tvItemProducto)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val vista = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_producto2, parent, false)
        return ProductoViewHolder(vista)
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val producto = productos[position]
        holder.tvProducto.text = producto
        holder.itemView.setOnClickListener { onClick(producto) }
    }

    override fun getItemCount(): Int = productos.size
}

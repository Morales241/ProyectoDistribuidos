package morales.jesus.appmovil.ui.preferencias

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import morales.jesus.appmovil.R
import morales.jesus.appmovil.dtos.CarritoProductoDTO

class CarritoAdapter(
    private val productos: List<CarritoProductoDTO>
) : RecyclerView.Adapter<CarritoAdapter.CarritoViewHolder>() {

    inner class CarritoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtProducto: TextView = itemView.findViewById(R.id.productoCarrito)
        val txtCantidad: TextView = itemView.findViewById(R.id.cantidadCarrito)
        val txtComercio: TextView = itemView.findViewById(R.id.comercioCarrito)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarritoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_carrito, parent, false)
        return CarritoViewHolder(view)
    }

    override fun onBindViewHolder(holder: CarritoViewHolder, position: Int) {
        val item = productos[position]
        holder.txtProducto.text = item.producto.producto
        holder.txtCantidad.text = "${item.cantidad} unidad(es)"
        holder.txtComercio.text = "$${item.producto.comercio}"
    }

    override fun getItemCount(): Int = productos.size
}

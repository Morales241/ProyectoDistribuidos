package morales.jesus.appmovil.ui.ofertas

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import morales.jesus.appmovil.R
import morales.jesus.appmovil.dtos.OfertaDTO
import java.time.format.DateTimeFormatter

class OfertaAdapter(private val lista: List<OfertaDTO>) :
    RecyclerView.Adapter<OfertaAdapter.OfertaViewHolder>() {

    inner class OfertaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvComercio: TextView = itemView.findViewById(R.id.tvComercio)
        val tvProducto: TextView = itemView.findViewById(R.id.tvProducto)
        val tvPrecio: TextView = itemView.findViewById(R.id.tvPrecio)
        val tvDescripcion: TextView = itemView.findViewById(R.id.tvDescripcion)
        val tvFecha: TextView = itemView.findViewById(R.id.tvFecha)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfertaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_oferta, parent, false)
        return OfertaViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: OfertaViewHolder, position: Int) {
        val oferta = lista[position]
        holder.tvComercio.text = oferta.comercio
        holder.tvProducto.text = "Producto: ${oferta.producto}"
        holder.tvPrecio.text = "Precio: $${oferta.precioOferta}"
        holder.tvDescripcion.text = "Descripción: ${oferta.descripcion}"
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
        val fechaInicioFormateada = oferta.fechaInicio.format(formatter)
        val fechaFinFormateada = oferta.fechaFin.format(formatter)

        holder.tvFecha.text = "Vigente: $fechaInicioFormateada a $fechaFinFormateada"
    }

    override fun getItemCount(): Int = lista.size
}

package dtos;

import entidades.ProductoWishList;

import java.util.ArrayList;
import java.util.List;

public class WishListDTO {

    private ConsumidorDTO consumidor;

    private String nombre;

    private List<ProductoWishListDTO> productos;

    public WishListDTO(){

        productos = new ArrayList<>();
    }

    public ConsumidorDTO getConsumidor() {
        return consumidor;
    }

    public void setConsumidor(ConsumidorDTO consumidor) {
        this.consumidor = consumidor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<ProductoWishListDTO> getProductos() {
        return productos;
    }

    public void setProductos(List<ProductoWishListDTO> productos) {
        this.productos = productos;
    }
}

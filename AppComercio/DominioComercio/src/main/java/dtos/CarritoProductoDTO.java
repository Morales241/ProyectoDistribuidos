package dtos;

public class CarritoProductoDTO {

    private PrecioProductoDTO producto;

    private int cantidad;

    public CarritoProductoDTO() {}

    public PrecioProductoDTO getProducto() {
        return producto;
    }

    public void setProducto(PrecioProductoDTO producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}

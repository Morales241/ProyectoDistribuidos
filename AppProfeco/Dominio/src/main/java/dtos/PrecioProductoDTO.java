package dtos;

public class PrecioProductoDTO {

    private ComercioDTO comercio;
    private ProductoDTO producto;

    private double precio;

    public PrecioProductoDTO() {}

    public ComercioDTO getComercio() {
        return comercio;
    }

    public void setComercio(ComercioDTO comercio) {
        this.comercio = comercio;
    }

    public ProductoDTO getProducto() {
        return producto;
    }

    public void setProducto(ProductoDTO producto) {
        this.producto = producto;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
}

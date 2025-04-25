package dtos;


public class ProductoWishListDTO {

    private PrecioProductoDTO producto;

    private WishListDTO wishlist;

    private int cantidad;

    public ProductoWishListDTO(){}

    public PrecioProductoDTO getProducto() {
        return producto;
    }

    public void setProducto(PrecioProductoDTO producto) {
        this.producto = producto;
    }

    public WishListDTO getWishlist() {
        return wishlist;
    }

    public void setWishlist(WishListDTO wishlist) {
        this.wishlist = wishlist;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}

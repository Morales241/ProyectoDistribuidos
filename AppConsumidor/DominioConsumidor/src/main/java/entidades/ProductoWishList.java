package entidades;

import jakarta.persistence.*;

@Entity
@Table(name = "Productos_De_WishList")
public class ProductoWishList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long idPrecioProducto;

    @ManyToOne
    @JoinColumn(nullable = false, name = "id_wishList")
    private WishList wishList;

    @Column(nullable = false)
    private int cantidad;

    public ProductoWishList() {}

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Long getIdPrecioProducto() {
        return idPrecioProducto;
    }

    public void setIdPrecioProducto(Long idPrecioProducto) {
        this.idPrecioProducto = idPrecioProducto;
    }

    public WishList getWishList() {
        return wishList;
    }

    public void setWishList(WishList wishList) {
        this.wishList = wishList;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}

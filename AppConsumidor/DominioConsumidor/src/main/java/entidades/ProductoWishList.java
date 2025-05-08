package entidades;

import jakarta.persistence.*;

@Entity
@Table(name = "Productos_De_WishList")
public class ProductoWishList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true)
    private String productos;

    @Column(nullable = true)
    private String servicios;

    @ManyToOne
    @JoinColumn(nullable = false, name = "id_wishList")
    private WishList wishList;

    public ProductoWishList() {}

    public String getProductos() {
        return productos;
    }

    public void setProductos(String productos) {
        this.productos = productos;
    }

    public String getServicios() {
        return servicios;
    }

    public void setServicios(String servicios) {
        this.servicios = servicios;
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

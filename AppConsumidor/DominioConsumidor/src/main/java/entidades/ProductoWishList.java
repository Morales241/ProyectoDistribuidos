package entidades;

import jakarta.persistence.*;

@Entity
@Table(name = "ProductosCarritoS")
public class ProductoWishList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long idProducto;

    @Column(nullable = false)
    private Long idWishList;

    @Column(nullable = false)
    private int cantidad;

    public ProductoWishList() {}

    public ProductoWishList(Long idWishList, Long idProducto) {
        this.idWishList = idWishList;
        this.idProducto = idProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Long getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Long idProducto) {
        this.idProducto = idProducto;
    }

    public Long getIdWishList() {
        return idWishList;
    }

    public void setIdWishList(Long idWishList) {
        this.idWishList = idWishList;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}

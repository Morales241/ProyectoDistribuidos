package entidades;

import jakarta.persistence.*;

@Entity
@Table(name = "Productos_De_Carrito")
public class ProductoCarrito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long idPrecioProducto;

    @Column(nullable = false)
    private int cantidad;

    @ManyToOne
    @JoinColumn(name = "carrito_id", nullable = false)
    private Carrito carrito;

    public Carrito getCarrito() {
        return carrito;
    }

    public void setCarrito(Carrito carrito) {
        this.carrito = carrito;
    }

    public ProductoCarrito() {}
    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdPrecioProducto() {
        return idPrecioProducto;
    }

    public void setIdPrecioProducto(Long idPrecioProducto) {
        this.idPrecioProducto = idPrecioProducto;
    }
}

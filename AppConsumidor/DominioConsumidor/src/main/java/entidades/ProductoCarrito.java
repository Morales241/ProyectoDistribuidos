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

    @ManyToOne
    @JoinColumn(nullable = false, name = "id_carrito")
    private Carrito carrito;

    @Column(nullable = false)
    private int cantidad;

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

    public Carrito getCarrito() {
        return carrito;
    }

    public void setCarrito(Carrito carrito) {
        this.carrito = carrito;
    }
}

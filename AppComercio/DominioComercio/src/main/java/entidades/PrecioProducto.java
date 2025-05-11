package entidades;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "PrecioProductos")
public class PrecioProducto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long idComercio;

    @Column(nullable = false)
    private Long idProducto;

    @Column(nullable = false)
    private double precio;

    public PrecioProducto() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProducto() {
        return idProducto;
    }

    public void setProducto(Long producto) {
        this.idProducto = producto;
    }

    public Long getComercio() {
        return idComercio;
    }

    public void setComercio(Long comercio) {
        this.idComercio = comercio;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

}

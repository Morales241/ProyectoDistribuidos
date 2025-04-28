package entidades;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "WishList")
public class Carrito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Consumidor consumidor;

    @Column(nullable = false)
    private Long productoId;

    @Column(nullable = false)
    private LocalDateTime fechaAgregado;

    public Carrito() {
    }

    public Carrito(LocalDateTime fechaAgregado, Long productoId, Consumidor consumidor) {
        this.fechaAgregado = fechaAgregado;
        this.productoId = productoId;
        this.consumidor = consumidor;
    }

    public Consumidor getConsumidor() {
        return consumidor;
    }

    public void setConsumidor(Consumidor consumidor) {
        this.consumidor = consumidor;
    }

    public Long getProductoId() {
        return productoId;
    }

    public void setProductoId(Long productoId) {
        this.productoId = productoId;
    }

    public LocalDateTime getFechaAgregado() {
        return fechaAgregado;
    }

    public void setFechaAgregado(LocalDateTime fechaAgregado) {
        this.fechaAgregado = fechaAgregado;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}

package entidades;

import dtos.ConsumidorDTO;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Productos_WishList")
public class ProductoWishList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String sugeriencia;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Consumidor consumidor;

    @Column(nullable = false)
    private Long idComercio;

    @Column(nullable = false)
    private LocalDateTime fecha;

    public ProductoWishList() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSugeriencia() {
        return sugeriencia;
    }

    public void setSugeriencia(String sugeriencia) {
        this.sugeriencia = sugeriencia;
    }

    public Consumidor getConsumidor() {
        return consumidor;
    }

    public void setConsumidor(Consumidor consumidor) {
        this.consumidor = consumidor;
    }

    public Long getIdComercio() {
        return idComercio;
    }

    public void setIdComercio(Long idComercio) {
        this.idComercio = idComercio;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
}

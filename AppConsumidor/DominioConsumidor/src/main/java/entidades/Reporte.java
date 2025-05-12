package entidades;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "Reportes")
public class Reporte implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private Long id;

    @Column(nullable = false)
    @JsonProperty("comercioId")
    private Long comercioId;

    @ManyToOne
    @JoinColumn(nullable = false)
    @JsonProperty("consumidor")
    private Consumidor consumidor;

    @Column(nullable = false)
    @JsonProperty("producto")
    private Long producto;

    @Column(nullable = false)
    @JsonProperty("contenido")
    private String contenido;

    @Column(nullable = false)
    @JsonProperty("fecha")
    private LocalDateTime fecha;

    public Reporte() {
    }

    public Reporte(Long comercioId, Consumidor consumidor, Long producto, String contenido) {
        this.comercioId = comercioId;
        this.consumidor = consumidor;
        this.producto = producto;
        this.contenido = contenido;
        this.fecha = LocalDateTime.now();
    }

    public Long getComercioId() {
        return comercioId;
    }

    public void setComercioId(Long comercioId) {
        this.comercioId = comercioId;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Long getComercio() {
        return comercioId;
    }

    public void setComercio(Long comercio) {
        this.comercioId = comercio;
    }

    public Consumidor getConsumidor() {
        return consumidor;
    }

    public void setConsumidor(Consumidor consumidor) {
        this.consumidor = consumidor;
    }

    public Long getProducto() {
        return producto;
    }

    public void setProducto(Long producto) {
        this.producto = producto;
    }

}

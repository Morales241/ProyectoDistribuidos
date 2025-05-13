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

    @ManyToOne
    @JoinColumn(nullable = false)
    @JsonProperty("consumidor")
    private Consumidor consumidor;

    @Column(nullable = false)
    @JsonProperty("producto")
    private Long precioProducto;

    @Column(nullable = false)
    @JsonProperty("contenido")
    private String contenido;

    @Column(nullable = false)
    @JsonProperty("fecha")
    private LocalDateTime fecha;

    public Reporte() {
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

    public Long getPrecioProducto() {
        return precioProducto;
    }

    public void setPrecioProducto(Long precioProducto) {
        this.precioProducto = precioProducto;
    }

    public Consumidor getConsumidor() {
        return consumidor;
    }

    public void setConsumidor(Consumidor consumidor) {
        this.consumidor = consumidor;
    }


}

package entidades;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Ofertas")
public class Oferta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long idPrecioProducto;

    @Column(nullable = false)
    private Double precioOferta;

    @Column(nullable = false)
    private Double precioAnterior;

    @Column(nullable = false)
    private LocalDateTime fechaInicio;

    @Column(nullable = false)
    private LocalDateTime fechaFin;

    @Column(nullable = false)
    private String descripcion;

    public Oferta() {}

    public Double getPrecioAnterior() {
        return precioAnterior;
    }

    public void setPrecioAnterior(Double precioAnterior) {
        this.precioAnterior = precioAnterior;
    }

    public Long getIdPrecioProducto() {
        return idPrecioProducto;
    }

    public void setIdPrecioProducto(Long idPrecioProducto) {
        this.idPrecioProducto = idPrecioProducto;
    }

    public Double getPrecioOferta() {
        return precioOferta;
    }

    public void setPrecioOferta(Double precioOferta) {
        this.precioOferta = precioOferta;
    }

    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDateTime getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDateTime fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}

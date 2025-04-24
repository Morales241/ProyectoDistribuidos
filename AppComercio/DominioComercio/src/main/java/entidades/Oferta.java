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
    private Long idComercio;

    @Column(nullable = false)
    private Long idProducto;

    @Column(nullable = false)
    private Double precioOferta;

    @Column(nullable = false)
    private LocalDateTime fechaInicio;

    @Column(nullable = false)
    private LocalDateTime fechaFin;

    @Column(nullable = false)
    private String descripcion;

    public Oferta() {}

    public Oferta(Long comercio, Long producto, Double precioOferta, LocalDateTime fechaInicio, LocalDateTime fechaFin, String descripcion) {
        this.idComercio = comercio;
        this.idProducto = producto;
        this.precioOferta = precioOferta;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.descripcion = descripcion;
    }

    public Long getComercio() {
        return idComercio;
    }

    public void setComercio(Long comercio) {
        this.idComercio = comercio;
    }

    public Long getProducto() {
        return idProducto;
    }

    public void setProducto(Long producto) {
        this.idProducto = producto;
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

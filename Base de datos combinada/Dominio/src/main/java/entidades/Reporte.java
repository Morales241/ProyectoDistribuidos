package entidades;

import jakarta.persistence.*;

@Entity
@Table(name = "Reportes")
public class Reporte {
    @Id
    private Long id;

    @ManyToOne
    @Column(nullable = false)
    private Comercio comercio;

    @ManyToOne
    @Column(nullable = false)
    private Consumidor consumidor;

    @ManyToOne
    @Column(nullable = false)
    private Producto producto;

    @ManyToOne
    @Column(nullable = true)
    private EmpleadoPROFECO Revisor;

    public Reporte() {}

    public Reporte(Comercio comercio, Consumidor consumidor, Producto producto) {
        this.comercio = comercio;
        this.consumidor = consumidor;
        this.producto = producto;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Comercio getComercio() {
        return comercio;
    }

    public void setComercio(Comercio comercio) {
        this.comercio = comercio;
    }

    public Consumidor getConsumidor() {
        return consumidor;
    }

    public void setConsumidor(Consumidor consumidor) {
        this.consumidor = consumidor;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public EmpleadoPROFECO getRevisor() {
        return Revisor;
    }

    public void setRevisor(EmpleadoPROFECO revisor) {
        Revisor = revisor;
    }
}

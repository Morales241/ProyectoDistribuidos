package entidades;

import jakarta.persistence.*;

@Entity
@Table(name = "Reportes")
public class Reporte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Comercio comercio;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Consumidor consumidor;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Producto producto;

    public Reporte() {
    }

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

}

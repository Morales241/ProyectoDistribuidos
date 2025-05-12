package entidades;

import jakarta.persistence.*;

@Entity
@Table(name = "PreferenciasDeComercios")
public class Preferencias {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long idPrecioProducto;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Consumidor consumidor;

    public Preferencias() {}

    public Long getIdPrecioProducto() {
        return idPrecioProducto;
    }

    public void setIdPrecioProducto(Long idPrecioProducto) {
        this.idPrecioProducto = idPrecioProducto;
    }

    public Consumidor getConsumidor() {
        return consumidor;
    }

    public void setConsumidor(Consumidor consumidor) {
        this.consumidor = consumidor;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}

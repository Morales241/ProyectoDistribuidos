package entidades;

import jakarta.persistence.*;

@Entity
@Table(name = "PreferenciasDeComercios")
public class Preferencias {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long idComercio;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Consumidor consumidor;

    public Preferencias() {}

    public Preferencias(Long idComercio, Consumidor consumidor) {
        this.idComercio = idComercio;
        this.consumidor = consumidor;
    }

    public Long getIdComercio() {
        return idComercio;
    }

    public void setIdComercio(Long idComercio) {
        this.idComercio = idComercio;
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

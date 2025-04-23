package entidades;

import jakarta.persistence.*;

@Entity
@Table(name = "Multas")
public class Multa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    @ManyToOne
    @JoinColumn(nullable = false)
    private Comercio comercio;

    @Column(nullable = false)
    private double montoMUlta;

    public Multa() {}

    public Multa(Comercio comercio, double montoMUlta) {
        this.comercio = comercio;
        this.montoMUlta = montoMUlta;
    }

    public Comercio getComercio() {
        return comercio;
    }

    public void setComercio(Comercio comercio) {
        this.comercio = comercio;
    }

    public double getMontoMUlta() {
        return montoMUlta;
    }

    public void setMontoMUlta(double montoMUlta) {
        this.montoMUlta = montoMUlta;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}

package entidades;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Multas")
public class Multa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long idComercio;

    @Column(nullable = false)
    private String motivo;

    @Column(nullable = false)
    private Double totalMulta;

    @Column(nullable = false)
    private LocalDateTime fecha;

    public Multa() {}

    public Multa(Long idComercio, String motivo, Double totalMulta, LocalDateTime fecha) {
        this.idComercio = idComercio;
        this.motivo = motivo;
        this.totalMulta = totalMulta;
        this.fecha = fecha;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdComercio() {
        return idComercio;
    }

    public void setIdComercio(Long idComercio) {
        this.idComercio = idComercio;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public Double getTotalMulta() {
        return totalMulta;
    }

    public void setTotalMulta(Double totalMulta) {
        this.totalMulta = totalMulta;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
}

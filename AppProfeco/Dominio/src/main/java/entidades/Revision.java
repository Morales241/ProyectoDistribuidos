package entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "Revisiones")
public class Revision {
    @Id
    private Long id;

    @Column(nullable = false)
    private Long idComercio;

    @Column(nullable = false)
    private Long idReporte;

    @Column(nullable = false)
    private String resultado;

    @Column(nullable = false)
    private LocalDate fecha;

    public Revision() {}

    public Revision(Long idComercio, Long idReporte, String resultado, LocalDate fecha) {
        this.idComercio = idComercio;
        this.idReporte = idReporte;
        this.resultado = resultado;
        this.fecha = fecha;
    }

    public Long getIdComercio() {
        return idComercio;
    }

    public void setIdComercio(Long idComercio) {
        this.idComercio = idComercio;
    }

    public Long getIdReporte() {
        return idReporte;
    }

    public void setIdReporte(Long idReporte) {
        this.idReporte = idReporte;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}

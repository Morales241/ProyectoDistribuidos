package dtos;

import java.time.LocalDate;

public class MultaDTO {

    private Long id;

    private ComercioDTO comercio;

    private String motivo;

    private Double totalMulta;

    private LocalDate fecha;

    public MultaDTO() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ComercioDTO getComercio() {
        return comercio;
    }

    public void setComercio(ComercioDTO comercio) {
        this.comercio = comercio;
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

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
}

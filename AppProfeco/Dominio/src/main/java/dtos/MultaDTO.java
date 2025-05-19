package dtos;

import java.time.LocalDate;

public class MultaDTO {

    private String comercio;

    private Double totalMulta;

    private LocalDate fecha;

    public MultaDTO() {}

    public String getComercio() {
        return comercio;
    }

    public void setComercio(String comercio) {
        this.comercio = comercio;
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

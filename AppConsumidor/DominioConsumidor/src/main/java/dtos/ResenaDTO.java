package dtos;

import java.time.LocalDateTime;

public class ResenaDTO {

    private String contenido;

    private String comercio;

    private Long idconsumidor;

    private int calificacion;

    private LocalDateTime fecha;

    public ResenaDTO() {}

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getComercio() {
        return comercio;
    }

    public void setComercio(String comercio) {
        this.comercio = comercio;
    }

    public Long getIdconsumidor() {
        return idconsumidor;
    }

    public void setIdconsumidor(Long idconsumidor) {
        this.idconsumidor = idconsumidor;
    }

    public int getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(int calificacion) {
        this.calificacion = calificacion;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
}

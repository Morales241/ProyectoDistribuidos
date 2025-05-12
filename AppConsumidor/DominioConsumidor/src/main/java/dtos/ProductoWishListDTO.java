package dtos;


import java.time.LocalDateTime;

public class ProductoWishListDTO {

    private String sugeriencia;

    private Long consumidor;

    private String nombreComercio;

    private LocalDateTime fecha;

    public ProductoWishListDTO(){}

    public String getSugeriencia() {
        return sugeriencia;
    }

    public void setSugeriencia(String sugeriencia) {
        this.sugeriencia = sugeriencia;
    }

    public Long getConsumidor() {
        return consumidor;
    }

    public void setConsumidor(Long consumidor) {
        this.consumidor = consumidor;
    }

    public String getNombreComercio() {
        return nombreComercio;
    }

    public void setNombreComercio(String nombreComercio) {
        this.nombreComercio = nombreComercio;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
}

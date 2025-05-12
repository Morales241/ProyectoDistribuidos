package dtos;


import java.time.LocalDateTime;

public class ProductoWishListDTO {

    private String sugeriencia;

    private ConsumidorDTO consumidor;

    private String nombreComercio;

    private LocalDateTime fecha;

    public ProductoWishListDTO(){}

    public String getSugeriencia() {
        return sugeriencia;
    }

    public void setSugeriencia(String sugeriencia) {
        this.sugeriencia = sugeriencia;
    }

    public ConsumidorDTO getConsumidor() {
        return consumidor;
    }

    public void setConsumidor(ConsumidorDTO consumidor) {
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

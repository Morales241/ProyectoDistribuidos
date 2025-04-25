package dtos;

public class PreferenciasDTO {

    private String nombreComercio;

    private ConsumidorDTO consumidor;

    public PreferenciasDTO() {}

    public String getNombreComercio() {
        return nombreComercio;
    }

    public void setNombreComercio(String nombreComercio) {
        this.nombreComercio = nombreComercio;
    }

    public ConsumidorDTO getConsumidor() {
        return consumidor;
    }

    public void setConsumidor(ConsumidorDTO consumidor) {
        this.consumidor = consumidor;
    }
}

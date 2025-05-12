package dtos;

public class PreferenciasDTO {

    private String comercio;

    private String producto;

    private ConsumidorDTO consumidor;

    public PreferenciasDTO() {}

    public String getComercio() {
        return comercio;
    }

    public void setComercio(String comercio) {
        this.comercio = comercio;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public ConsumidorDTO getConsumidor() {
        return consumidor;
    }

    public void setConsumidor(ConsumidorDTO consumidor) {
        this.consumidor = consumidor;
    }
}

package morales.jesus.appmovil.dtos;

public class ResenaDTO {
    private String contenido;
    private String comercio;
    private Long idconsumidor;
    private int calificacion;
    private String fecha;

    public ResenaDTO(String contenido, String comercio, Long idconsumidor, int calificacion) {
        this.contenido = contenido;
        this.comercio = comercio;
        this.idconsumidor = idconsumidor;
        this.calificacion = calificacion;
        this.fecha = null;
    }
}

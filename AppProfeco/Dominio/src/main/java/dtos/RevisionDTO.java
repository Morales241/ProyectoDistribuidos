package dtos;

import java.time.LocalDate;

public class RevisionDTO {

    private Long id;

    private ReporteDTO reporte;

    private String resultado;

    private LocalDate fecha;

    public RevisionDTO() {}

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public ReporteDTO getReporte() { return reporte; }

    public void setReporte(ReporteDTO idReporte) { this.reporte = idReporte; }

    public String getResultado() { return resultado; }

    public void setResultado(String resultado) { this.resultado = resultado; }

    public LocalDate getFecha() { return fecha; }

    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
}

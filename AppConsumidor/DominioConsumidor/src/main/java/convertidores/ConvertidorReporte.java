package convertidores;

import dtos.ConsumidorDTO;
import dtos.ReporteDTO;
import entidades.Consumidor;
import entidades.Reporte;
import feings.ComercioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConvertidorReporte extends Convertidor<ReporteDTO, Reporte> {

    @Autowired
    private static ComercioClient clienteComercio;
    private static Convertidor<ConsumidorDTO, Consumidor> convertidorConsumidor = new ConvertidorConsumidor();

    public ConvertidorReporte() {
        super(ConvertidorReporte::convertToEntity, ConvertidorReporte::convertToDto);
    }

    private static ReporteDTO convertToDto(Reporte reporte) {
        ReporteDTO dto = new ReporteDTO();
        dto.setFecha(reporte.getFecha());
        dto.setContenido(reporte.getContenido());
        dto.setComercio(clienteComercio.obtener(reporte.getComercioId()).getBody());
        dto.setConsumidor(convertidorConsumidor.convertFromEntity(reporte.getConsumidor()));
        dto.setProducto(clienteComercio.findByProductoId(reporte.getProducto()).getBody().getFirst());

        return dto;
    }

    private static Reporte convertToEntity(ReporteDTO dto) {
        Reporte reporte = new Reporte();
        reporte.setFecha(dto.getFecha());
        reporte.setContenido(dto.getContenido());
        reporte.setConsumidor(convertidorConsumidor.convertFromDto(dto.getConsumidor()));
        reporte.setComercioId(dto.getComercio().getId());
        // Falta conseguir el id del producto
        reporte.setProducto(2L);

        return reporte;
    }
}

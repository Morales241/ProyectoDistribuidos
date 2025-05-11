package mappers;

import dtos.PrecioProductoDTO;
import entidades.PrecioProducto;
import org.springframework.beans.factory.annotation.Autowired;
import servicios.ComercioService;
import servicios.ProductoService;

public class ConvertidorPrecioProducto extends Convertidor<PrecioProductoDTO, PrecioProducto> {

    public ConvertidorPrecioProducto() {
        super(ConvertidorPrecioProducto::PPtoEntity, ConvertidorPrecioProducto::PPtoDTO);
    }

    private static PrecioProductoDTO PPtoDTO(PrecioProducto p) {
        PrecioProductoDTO dto = new PrecioProductoDTO();
        dto.setPrecio(p.getPrecio());
        return dto;
    }

    private static PrecioProducto PPtoEntity(PrecioProductoDTO dto) {
        PrecioProducto p = new PrecioProducto();
        p.setPrecio(dto.getPrecio());
        return p;
    }
}

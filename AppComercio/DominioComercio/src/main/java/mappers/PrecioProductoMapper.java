package mappers;


import dtos.PrecioProductoDTO;
import entidades.PrecioProducto;
import servicios.ComercioService;
import servicios.ProductoService;

public class PrecioProductoMapper {

    public static PrecioProducto toEntity(PrecioProductoDTO dto) {

        PrecioProducto pp = new PrecioProducto();
        pp.setId(dto.getId());
        pp.setPrecio(dto.getPrecio());
        pp.setProducto(dto.getProducto());
        pp.setComercio(dto.getComercio());
        pp.setFecha(dto.getFecha());

        return pp;
    }

    public static PrecioProductoDTO toDTO(PrecioProducto pp) {

        PrecioProductoDTO ppdto = new PrecioProductoDTO();
        ppdto.setId(pp.getId());
        ppdto.setPrecio(pp.getPrecio());
        ppdto.setProducto(pp.getProducto());
        ppdto.setComercio(pp.getComercio());
        ppdto.setFecha(pp.getFecha());
        return ppdto;
    }
}

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
        pp.setProducto(dto.getProducto().getId());
        pp.setComercio(dto.getComercio().getId());
        pp.setFecha(dto.getFecha());

        return pp;
    }

    public static PrecioProductoDTO toDTO(PrecioProducto pp, ProductoService productoService, ComercioService comercioService) {

        PrecioProductoDTO ppdto = new PrecioProductoDTO();
        ppdto.setId(pp.getId());
        ppdto.setPrecio(pp.getPrecio());
        ppdto.setProducto(ProductoMapper.toDTO(productoService.findById(pp.getProducto())));
        ppdto.setComercio(ComercioMapper.toDTO(comercioService.buscarComercioPorId(pp.getProducto())));
        ppdto.setFecha(pp.getFecha());
        return ppdto;
    }
}

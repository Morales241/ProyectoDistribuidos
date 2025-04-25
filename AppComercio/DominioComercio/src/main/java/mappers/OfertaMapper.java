package mappers;


import dtos.OfertaDTO;
import entidades.Oferta;
import servicios.ComercioService;
import servicios.OfertaService;
import servicios.ProductoService;

public class OfertaMapper {

    public static Oferta toEntity(OfertaDTO dto) {

        Oferta oferta = new Oferta();
        oferta.setId(dto.getId());
        oferta.setComercio(dto.getComercio().getId());
        oferta.setPrecioOferta(dto.getPrecioOferta());
        oferta.setDescripcion(dto.getDescripcion());
        oferta.setProducto(dto.getProducto().getId());
        oferta.setFechaFin(dto.getFechaFin());
        oferta.setFechaInicio(dto.getFechaInicio());
        return oferta;
    }

    public static OfertaDTO toDTO(Oferta oferta, ComercioService comercioService, ProductoService productoService) {

        OfertaDTO ofertadto = new OfertaDTO();
        ofertadto.setId(oferta.getId());
        ofertadto.setComercio(ComercioMapper.toDTO(comercioService.buscarComercioPorId(oferta.getComercio())));
        ofertadto.setPrecioOferta(oferta.getPrecioOferta());
        ofertadto.setDescripcion(oferta.getDescripcion());
        ofertadto.setProducto(ProductoMapper.toDTO(productoService.findById(oferta.getProducto())));
        ofertadto.setFechaFin(oferta.getFechaFin());
        ofertadto.setFechaInicio(oferta.getFechaInicio());
        return ofertadto;
    }
}

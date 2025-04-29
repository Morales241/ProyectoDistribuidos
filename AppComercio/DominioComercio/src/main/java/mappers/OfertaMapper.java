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
        oferta.setComercio(dto.getComercio());
        oferta.setPrecioOferta(dto.getPrecioOferta());
        oferta.setDescripcion(dto.getDescripcion());
        oferta.setProducto(dto.getProducto());
        oferta.setFechaFin(dto.getFechaFin());
        oferta.setFechaInicio(dto.getFechaInicio());
        return oferta;
    }

    public static OfertaDTO toDTO(Oferta oferta) {

        OfertaDTO ofertadto = new OfertaDTO();
        ofertadto.setId(oferta.getId());
        ofertadto.setComercio(oferta.getComercio());
        ofertadto.setPrecioOferta(oferta.getPrecioOferta());
        ofertadto.setDescripcion(oferta.getDescripcion());
        ofertadto.setProducto(oferta.getProducto());
        ofertadto.setFechaFin(oferta.getFechaFin());
        ofertadto.setFechaInicio(oferta.getFechaInicio());
        return ofertadto;
    }
}

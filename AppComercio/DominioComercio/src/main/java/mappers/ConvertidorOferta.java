package mappers;

import dtos.OfertaDTO;
import dtos.PrecioProductoDTO;
import entidades.Comercio;
import entidades.Oferta;
import entidades.PrecioProducto;
import entidades.Producto;

import java.util.Optional;

public class ConvertidorOferta extends Convertidor<OfertaDTO, Oferta>{

    private static Convertidor<PrecioProductoDTO, PrecioProducto> convertidor = new ConvertidorPrecioProducto();

    public ConvertidorOferta() {
        super(ConvertidorOferta::convertToOferta, ConvertidorOferta::convertToDTO);
    }

    private static OfertaDTO convertToDTO(Oferta oferta) {

        OfertaDTO ofertaDTO = new OfertaDTO();
        ofertaDTO.setDescripcion(oferta.getDescripcion());
        ofertaDTO.setPrecioOferta(oferta.getPrecioOferta());
        ofertaDTO.setFechaInicio(oferta.getFechaInicio());
        ofertaDTO.setFechaFin(oferta.getFechaFin());
        return ofertaDTO;
    }

    private static Oferta convertToOferta(OfertaDTO ofertaDTO) {

        Oferta oferta = new Oferta();
        oferta.setDescripcion(ofertaDTO.getDescripcion());
        oferta.setPrecioOferta(oferta.getPrecioOferta());
        oferta.setFechaInicio(oferta.getFechaInicio());
        oferta.setFechaFin(oferta.getFechaFin());
        return oferta;
    }
}

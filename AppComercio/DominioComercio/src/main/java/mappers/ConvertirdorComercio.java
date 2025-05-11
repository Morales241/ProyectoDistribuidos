package mappers;

import dtos.ComercioDTO;
import entidades.Comercio;

public class ConvertirdorComercio extends Convertidor<ComercioDTO, Comercio> {

    public ConvertirdorComercio() {
        super(ConvertirdorComercio::convertToEntity, ConvertirdorComercio::convertToDTO);
    }

    private static ComercioDTO convertToDTO(Comercio comercio) {
        ComercioDTO dto = new ComercioDTO();
        dto.setNombre(comercio.getNombre());
        dto.setTipo(comercio.getTipo());
        return dto;
    }

    private static Comercio convertToEntity(ComercioDTO dto){

        Comercio comercio = new Comercio();
        comercio.setNombre(dto.getNombre());
        comercio.setTipo(dto.getTipo());
        return comercio;
    }
}

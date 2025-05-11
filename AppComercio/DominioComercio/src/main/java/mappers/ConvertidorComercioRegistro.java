package mappers;

import dtos.ComercioDTO;
import dtos.RegistroDTO;
import entidades.Comercio;

public class ConvertidorComercioRegistro extends Convertidor<RegistroDTO, Comercio> {

    public ConvertidorComercioRegistro() {
        super(ConvertidorComercioRegistro::convertToEntity, ConvertidorComercioRegistro::convertToDTO);
    }

    private static RegistroDTO convertToDTO(Comercio comercio) {
        RegistroDTO dto = new RegistroDTO();
        dto.setNombre(comercio.getNombre());
        dto.setTipo(comercio.getTipo());
        dto.setCorreo(comercio.getCorreo());
        return dto;
    }

    private static Comercio convertToEntity(RegistroDTO dto){

        Comercio comercio = new Comercio();
        comercio.setNombre(dto.getNombre());
        comercio.setContrasena(dto.getContrasena());
        comercio.setTipo(dto.getTipo());
        comercio.setCorreo(dto.getCorreo());
        return comercio;
    }
}

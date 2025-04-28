package mappers;

import dtos.ComercioDTO;
import entidades.Comercio;

public class ComercioMapper {

    public static Comercio toEntity(ComercioDTO dto) {

        Comercio comercio = new Comercio();
        if (dto.getId() != null) {
            comercio.setId(dto.getId());
        }

        if (dto.getNombre() != null) {
            comercio.setNombre(dto.getNombre());
        }

        if (dto.getTipo() != null) {
            comercio.setTipo(dto.getTipo());
        }

        if (dto.getUsurio() != null) {
            comercio.setCorreo(dto.getUsurio());
        }

        if (dto.getContra() != null) {
            comercio.setContrasena(dto.getContra());
        }

        return comercio;
    }

    public static ComercioDTO toDTO(Comercio comercio) {

        ComercioDTO comerciodto = new ComercioDTO();
        comerciodto.setId(comercio.getId());
        comerciodto.setNombre(comercio.getNombre());
        comerciodto.setCorreo(comercio.getCorreo());
        comerciodto.setTipo(comercio.getTipo());
        return comerciodto;
    }
}

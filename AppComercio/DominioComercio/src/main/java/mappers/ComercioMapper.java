package mappers;

import dtos.ComercioDTO;
import entidades.Comercio;

public class ComercioMapper {

    public static Comercio toEntity(ComercioDTO dto) {

        Comercio comercio = new Comercio();
        comercio.setId(dto.getId());
        comercio.setNombre(dto.getNombre());
        comercio.setAuthId(dto.getAuthId());
        comercio.setTipo(dto.getTipo());
        return comercio;
    }

    public static ComercioDTO toDTO(Comercio comercio) {

        ComercioDTO comerciodto = new ComercioDTO();
        comerciodto.setId(comercio.getId());
        comerciodto.setNombre(comercio.getNombre());
        comerciodto.setAuthId(comercio.getAuthId());
        comerciodto.setTipo(comercio.getTipo());
        return comerciodto;
    }
}

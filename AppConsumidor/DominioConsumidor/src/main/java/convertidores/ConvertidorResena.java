package convertidores;

import dtos.ResenaDTO;
import entidades.Resena;


public class ConvertidorResena extends Convertidor<ResenaDTO, Resena>{

    public ConvertidorResena() {
        super(ConvertidorResena::convertToEntity, ConvertidorResena::convertToDto);
    }

    private static ResenaDTO convertToDto(Resena resena) {

        ResenaDTO dto = new ResenaDTO();
        dto.setFecha(resena.getFecha());
        dto.setContenido(resena.getContenido());
        dto.setCalificacion(resena.getCalificacion());
        return dto;
    }

    private static Resena convertToEntity(ResenaDTO dto) {

        Resena resena = new Resena();
        resena.setFecha(dto.getFecha());
        resena.setContenido(dto.getContenido());
        resena.setCalificacion(dto.getCalificacion());
        return resena;
    }
}

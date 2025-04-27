package convertidores;

import dtos.ConsumidorDTO;
import entidades.Consumidor;

public class ConvertidorConsumidor  extends Convertidor<ConsumidorDTO, Consumidor> {

    public ConvertidorConsumidor() {
        super(ConvertidorConsumidor::convertToEntity, ConvertidorConsumidor::convertToDto);
    }

    private static ConsumidorDTO convertToDto(Consumidor consumidor) {
        ConsumidorDTO dto = new ConsumidorDTO();
        dto.setCorreo(consumidor.getCorreo());
        dto.setNombre(consumidor.getNombre());
        dto.setFechaRegistro(consumidor.getFechaRegistro());

        return dto;
    }

    private static Consumidor convertToEntity(ConsumidorDTO dto) {
        Consumidor consumidor = new Consumidor();
        consumidor.setNombre(dto.getNombre());
        consumidor.setCorreo(dto.getCorreo());
        consumidor.setFechaRegistro(dto.getFechaRegistro());

        return consumidor;
    }
}

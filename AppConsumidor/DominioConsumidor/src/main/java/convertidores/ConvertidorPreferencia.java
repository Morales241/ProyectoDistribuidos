package convertidores;

import dtos.ConsumidorDTO;
import dtos.PreferenciasDTO;
import entidades.Consumidor;
import entidades.Preferencias;


public class ConvertidorPreferencia extends Convertidor<PreferenciasDTO, Preferencias>{

    private static Convertidor<ConsumidorDTO, Consumidor> convertidorConsumidor = new ConvertidorConsumidor();

    public ConvertidorPreferencia() {
        super(ConvertidorPreferencia::convertToEntity, ConvertidorPreferencia::convertToDto);
    }

    private static PreferenciasDTO convertToDto(Preferencias preferencia) {
        PreferenciasDTO dto = new PreferenciasDTO();
        dto.setConsumidor(convertidorConsumidor.convertFromEntity(preferencia.getConsumidor()));

        return dto;
    }

    private static Preferencias convertToEntity(PreferenciasDTO dto) {
        Preferencias preferencia = new Preferencias();
        preferencia.setConsumidor(convertidorConsumidor.convertFromDto(dto.getConsumidor()));
        return preferencia;
    }
}

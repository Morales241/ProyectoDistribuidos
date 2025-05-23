package convertidores;

import dtos.MultaDTO;
import entidades.Multa;
import feings.ComercioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConvertidorMulta extends Convertidor<MultaDTO, Multa> {

    @Autowired
    private static ComercioClient clienteComercio;

    public ConvertidorMulta() {
        super(ConvertidorMulta::convertToEntity, ConvertidorMulta::convertToDto);
    }

    private static MultaDTO convertToDto(Multa multa) {
        MultaDTO dto = new MultaDTO();
        dto.setFecha(multa.getFecha());
        dto.setTotalMulta(multa.getTotalMulta());
        return dto;
    }

    private static Multa convertToEntity(MultaDTO dto) {
        Multa multa = new Multa();
        multa.setFecha(dto.getFecha());
        multa.setTotalMulta(dto.getTotalMulta());
        return multa;
    }
}

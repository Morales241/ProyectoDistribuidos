package convertidores;

import dtos.RevisionDTO;
import entidades.Revision;
import feings.ReporteClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConvertidorRevision extends Convertidor<RevisionDTO, Revision> {

    @Autowired
    private static ReporteClient clienteReporte;

    public ConvertidorRevision() {
        super(ConvertidorRevision::convertToEntity, ConvertidorRevision::convertToDto);
    }

    private static RevisionDTO convertToDto(Revision revision) {
        RevisionDTO dto = new RevisionDTO();
        dto.setFecha(revision.getFecha());
        dto.setResultado(revision.getResultado());
        dto.setReporte(clienteReporte.obtener(revision.getIdReporte()).getBody());

        return dto;
    }

    private static Revision convertToEntity(RevisionDTO dto) {
        Revision revision = new Revision();

        return revision;
    }
}

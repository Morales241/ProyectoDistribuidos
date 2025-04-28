package servicios;

import entidades.Revision;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositorios.RevisionRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class RevisionService {

    @Autowired
    private RevisionRepository revisionRepository;

    public List<Revision> findRevisiones() {
        return revisionRepository.findAll();
    }

    public List<Revision> findRevisionesByComercioId(Long comercioId) {
        return revisionRepository.findByIdComercio(comercioId);
    }

    public List<Revision> findRevisionesBySpecificFecha(LocalDate fecha){
        return  revisionRepository.findByFecha(fecha);
    }

    public Revision guardarRevision(Revision revision) {
        return revisionRepository.save(revision);
    }

    public void eliminarRevision(Long multaId) {
        revisionRepository.deleteById(multaId);
    }

    public List<Revision> findRevisionesByPeriodo(LocalDate fechaInicio, LocalDate fechaFin) {
        return revisionRepository.findByFechaBetween(fechaInicio, fechaFin);
    }

}

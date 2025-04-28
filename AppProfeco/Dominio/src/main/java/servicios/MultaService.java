package servicios;

import entidades.Multa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositorios.MultaRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class MultaService {

    @Autowired
    private MultaRepository multaRepository;

    public List<Multa> findMultas() {
        return multaRepository.findAll();
    }

    public List<Multa> findMultasByComercioId(Long comercioId) {
        return multaRepository.findByIdComercio(comercioId);
    }

    public List<Multa> findMultasBySpecificFecha(LocalDate fecha){
        return  multaRepository.findByFecha(fecha);
    }

    public Multa guardarMulta(Multa multa) {
        return multaRepository.save(multa);
    }

    public void eliminarMulta(Long multaId) {
        multaRepository.deleteById(multaId);
    }

    public List<Multa> findMultasByPeriodo(LocalDate fechaInicio, LocalDate fechaFin) {
        return multaRepository.findByFechaBetween(fechaInicio, fechaFin);
    }
}

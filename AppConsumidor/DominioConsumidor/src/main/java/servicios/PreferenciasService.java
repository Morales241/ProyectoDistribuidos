package servicios;

import entidades.Preferencias;
import excepciones.ConsumidorServiciosException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositorios.PreferenciasRepository;

import java.util.List;

@Service
public class PreferenciasService {

    @Autowired
    private PreferenciasRepository preferenciasRepository;

    public Preferencias agregarPreferencia(Preferencias preferencia) throws ConsumidorServiciosException {
        if (preferenciasRepository.existsByConsumidorIdAndIdComercio(
                preferencia.getConsumidor().getId(),
                preferencia.getIdComercio())) {
            throw new ConsumidorServiciosException("El comercio ya se encuentra en la lista de preferencias");
        }

        return preferenciasRepository.save(preferencia);
    }

    public List<Preferencias> obtenerPreferencias(Long idConsumidor) {
        return preferenciasRepository.findByConsumidorId(idConsumidor);
    }

    public void eliminarPreferencia(Long id) {
        preferenciasRepository.deleteById(id);
    }
}

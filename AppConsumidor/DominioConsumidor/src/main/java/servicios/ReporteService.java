package servicios;

import entidades.Reporte;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositorios.ReporteRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReporteService {

    @Autowired
    private ReporteRepository reporteRepository;

    public Reporte crearReporte(Reporte reporte) {
        reporte.setFecha(LocalDateTime.now());
        return reporteRepository.save(reporte);
    }

    public List<Reporte> obtenerPorConsumidor(Long idConsumidor) {
        return reporteRepository.findByConsumidorId(idConsumidor);
    }

    public List<Reporte> obtenerPorComercio(Long idComercio) {
        return reporteRepository.findByComercioId(idComercio);
    }
}

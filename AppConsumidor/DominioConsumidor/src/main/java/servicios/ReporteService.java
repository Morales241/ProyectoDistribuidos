package servicios;

import entidades.Reporte;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import repositorios.ReporteRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReporteService {

    @Autowired
    private ReporteRepository reporteRepository;

    @Autowired
    private RabbitTemplate template;

    @Value("reportes")
    private String topic;

    @Value("reportesKey")
    private String llave;

    public Reporte crearReporte(Reporte reporte) {
        reporte.setFecha(LocalDateTime.now());
        template.convertAndSend(topic, llave, reporte);
        return reporteRepository.save(reporte);
    }

    public List<Reporte> obtenerPorConsumidor(Long idConsumidor) {
        return reporteRepository.findByConsumidorId(idConsumidor);
    }

    public List<Reporte> obtenerPorComercio(Long idComercio) {
        return reporteRepository.findByComercioId(idComercio);
    }

    public Optional<Reporte> obtener(Long id) {
        return reporteRepository.findById(id);
    }
}

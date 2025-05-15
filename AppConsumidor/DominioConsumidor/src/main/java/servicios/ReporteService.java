package servicios;

import entidades.Reporte;
import lombok.extern.java.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import repositorios.ReporteRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class ReporteService {

    Logger log = Logger.getLogger(ReporteService.class.getName());

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
        log.info("se va a enviar el reporte a la cola topic:"+topic+" llave:"+llave+" reporte:"+reporte);
        template.convertAndSend(topic, llave, reporte);
        return reporteRepository.save(reporte);
    }

    public List<Reporte> obtenerPorConsumidor(Long idConsumidor) {
        return reporteRepository.findByConsumidorId(idConsumidor);
    }

    public List<Reporte> obtenerPorPrecioProducto(Long idPP) {
        return reporteRepository.findByPrecioProducto(idPP);
    }

    public Optional<Reporte> obtener(Long id) {
        return reporteRepository.findById(id);
    }
}

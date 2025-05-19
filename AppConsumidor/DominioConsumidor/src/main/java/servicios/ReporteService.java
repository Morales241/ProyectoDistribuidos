package servicios;

import dtos.PrecioProductoDTO;
import dtos.ProductoDTO;
import dtos.ReporteMensajeDTO;
import entidades.Reporte;
import feings.ComercioClient;
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

    @Autowired
    private ComercioClient comercioClient;

    public Reporte crearReporte(Reporte reporte) {
        reporte.setFecha(LocalDateTime.now());
        ReporteMensajeDTO mensaje = new ReporteMensajeDTO();
        PrecioProductoDTO producto = comercioClient.traerProductoEspecificoPorId(reporte.getPrecioProducto()).getBody();

        mensaje.setFecha(LocalDateTime.now());
        mensaje.setComercio(producto.getComercio());
        mensaje.setProducto(producto.getProducto());
        mensaje.setContenido(reporte.getContenido());

        template.convertAndSend(topic, llave, mensaje);

        reporte.setEstado(true);
        return reporteRepository.save(reporte);
    }

    public List<Reporte> obtenerPorConsumidor(Long idConsumidor) {
        return reporteRepository.findByConsumidorIdAndEstado(idConsumidor, true);
    }

    public List<Reporte> obtenerTodosLosReportes(){
        return reporteRepository.findAllByEstado(true);
    }

    public List<Reporte> obtenerPorPrecioProducto(Long idPP) {
        return reporteRepository.findByPrecioProductoAndEstado(idPP, true);
    }

    public Optional<Reporte> obtener(Long id) {
        return reporteRepository.findById(id);
    }

    public void invalidarReporte(Long precioProducto, String contenido, LocalDateTime fecha){
        Reporte reporte = reporteRepository.findByPrecioProductoAndContenidoAndFecha(precioProducto, contenido, fecha);

        reporte.setEstado(false);

        reporteRepository.save(reporte);

    }
}

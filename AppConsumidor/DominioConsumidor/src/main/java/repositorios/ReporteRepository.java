package repositorios;

import entidades.Reporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReporteRepository extends JpaRepository<Reporte, Long> {

    List<Reporte> findByConsumidorIdAndEstado(Long consumidorId, Boolean estado);

    List<Reporte> findByPrecioProductoAndEstado(Long precioProducto, Boolean estado);

    Reporte findByPrecioProductoAndContenidoAndFecha(Long precioProducto, String contenido, LocalDateTime fecha);

    List<Reporte> findAllByEstado(Boolean estado);
}

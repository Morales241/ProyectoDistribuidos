package repositorios;

import entidades.Reporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReporteRepository extends JpaRepository<Reporte, Long> {

    List<Reporte> findByConsumidorId(Long consumidorId);

    List<Reporte> findByPrecioProducto(Long precioProducto);

}

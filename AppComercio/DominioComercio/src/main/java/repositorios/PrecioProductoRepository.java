package repositorios;

import entidades.PrecioProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PrecioProductoRepository extends JpaRepository<PrecioProducto, Long> {

    Optional<PrecioProducto> findByIdProductoAndIdComercio(Long producto, Long comercio);

    List<PrecioProducto> findByidComercio(Long comercio);

    List<PrecioProducto> findByidProducto(Long producto);

}

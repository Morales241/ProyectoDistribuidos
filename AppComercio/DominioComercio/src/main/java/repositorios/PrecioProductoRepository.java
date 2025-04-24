package repositorios;

import entidades.PrecioProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PrecioProductoRepository extends JpaRepository<PrecioProducto, Long> {

    Optional<PrecioProducto> findByProductoAndComercio(Long producto, Long comercio);

    List<PrecioProducto> findByComercio(Long comercio);

    List<PrecioProducto> findByProducto(Long producto);

}

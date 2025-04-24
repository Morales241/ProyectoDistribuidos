package repositorios;

import entidades.ProductoCarrito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoCarritoRepository extends JpaRepository<ProductoCarrito, Long> {
    List<ProductoCarrito> findByCarritoId(Long carritoId);
}

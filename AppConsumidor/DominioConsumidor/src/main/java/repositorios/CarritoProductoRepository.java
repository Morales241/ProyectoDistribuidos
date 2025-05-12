package repositorios;

import entidades.Carrito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import entidades.ProductoCarrito;

import java.util.List;

@Repository
public interface CarritoProductoRepository extends JpaRepository<ProductoCarrito, Long> {

}

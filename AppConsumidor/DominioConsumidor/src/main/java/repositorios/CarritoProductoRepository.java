package repositorios;

import entidades.Carrito;
import entidades.ProductoCarrito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarritoProductoRepository extends JpaRepository<ProductoCarrito, Long> {

    public List<ProductoCarrito> findByCarrito(Long carrito);
}

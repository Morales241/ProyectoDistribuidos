package repositorios;

import entidades.Carrito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishListRepository extends JpaRepository<Carrito, Long> {

    List<Carrito> findByConsumidorId(Long consumidor);

    List<Carrito> findByProductoId(Long producto);

    boolean existsByConsumidorIdAndProductoId(Long consumidorId, Long productoId);


}

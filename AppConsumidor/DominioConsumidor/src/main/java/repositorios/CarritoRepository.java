package repositorios;

import entidades.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarritoRepository extends JpaRepository<WishList, Long> {

    List<WishList> findByConsumidorId(Long consumidorId);
}

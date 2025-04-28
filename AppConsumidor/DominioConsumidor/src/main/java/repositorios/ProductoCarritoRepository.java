package repositorios;

import entidades.ProductoWishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoCarritoRepository extends JpaRepository<ProductoWishList, Long> {
    List<ProductoWishList> findByCarritoId(Long id);
}

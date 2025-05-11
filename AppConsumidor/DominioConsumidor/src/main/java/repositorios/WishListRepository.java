package repositorios;

import entidades.Consumidor;
import entidades.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishListRepository extends JpaRepository<WishList, Long> {

    public List<WishList> findByConsumidorId(Long consumidor);

    public WishList findByNombreDeMercadoAndConsumidor(String nombreDeMercado, Consumidor consumidor);



}

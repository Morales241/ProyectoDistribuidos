package feigns;

import dtos.CarritoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient("DOMINIOCONSUMIDOR")
public interface ConsumidorClient {

    @GetMapping("/wishList/obtenerPorProducto/{idProducto}")
    public ResponseEntity<List<CarritoDTO>> obtenerWishlistPorProducto(@PathVariable Long idProducto);
}

package feigns;

import dtos.CarritoDTO;
import dtos.ConsumidorDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient("DOMINIOCONSUMIDOR")
public interface ConsumidorClient {

    @GetMapping("/consumidores/traerConsumidores")
    public ResponseEntity<List<ConsumidorDTO>> traerConsumidores();
}

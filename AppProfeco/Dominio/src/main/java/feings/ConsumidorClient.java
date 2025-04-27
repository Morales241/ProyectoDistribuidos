package feings;

import dtos.ConsumidorDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("DOMINIOCONSUMIDOR")
public interface ConsumidorClient {

    @GetMapping("/consumidores/{id}")
    public ResponseEntity<ConsumidorDTO> obtener(@PathVariable Long id);
}

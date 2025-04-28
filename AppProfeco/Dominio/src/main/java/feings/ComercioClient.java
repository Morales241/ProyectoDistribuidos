package feings;

import dtos.ComercioDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("DOMINIOCOMERCIO")
public interface ComercioClient {

    @GetMapping("/comercios/{id}")
    ResponseEntity<ComercioDTO> obtener(@PathVariable Long id);
}

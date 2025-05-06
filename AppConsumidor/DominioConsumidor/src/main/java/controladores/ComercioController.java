package controladores;

import dtos.ComercioDTO;
import dtos.ProductoDTO;
import entidades.Consumidor;
import feings.ComercioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/consumidoresComercio")
public class ComercioController {

    @Autowired
    private ComercioClient clienteComercio;

    @GetMapping("/buscarComercioPorNombre")
        public ResponseEntity<ComercioDTO> obtenerComercioPorNombre(@RequestBody String nombre) {
        return ResponseEntity.ok(clienteComercio.buscarComercioPornombre(nombre).getBody());
    }

    @GetMapping("/buscarProductoPorNombre")
    public ResponseEntity<ProductoDTO> obtenerProductosPorNombre(@RequestBody String nombre) {
        return ResponseEntity.ok(clienteComercio.buscarProductoPornombre(nombre).getBody());
    }

    @GetMapping("/buscarProductos")
    public ResponseEntity<List<ProductoDTO>> obtenerProductos() {
        return org.springframework.http.ResponseEntity.ok(clienteComercio.listarProductos().getBody());
    }

}

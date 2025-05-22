package controladores;

import dtos.ComercioDTO;
import dtos.PrecioProductoDTO;
import dtos.ProductoDTO;
import feings.ComercioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

//@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5174", "http://localhost:5175"})
@RestController
@RequestMapping("/consumidoresComercio")
public class ComercioController {

    @Autowired
    private ComercioClient clienteComercio;

    @GetMapping("/buscarComercioPorNombre")
        public ResponseEntity<ComercioDTO> obtenerComercioPorNombre(@RequestParam String nombre) {
        return ResponseEntity.ok(clienteComercio.buscarComercioPornombre(nombre).getBody());
    }

    @GetMapping("/buscarProductoPorNombre")
    public ResponseEntity<ProductoDTO> obtenerProductosPorNombre(@RequestParam String nombre) {
        return ResponseEntity.ok(clienteComercio.buscarProductoPornombre(nombre).getBody());
    }

    @GetMapping("/traerComercios")
    public ResponseEntity<List<ComercioDTO>> traerComercios() {
        List<ComercioDTO> comercios = new ArrayList<>();
        comercios = clienteComercio.traerComercios().getBody();
        return ResponseEntity.ok(comercios);
    }

   @GetMapping("/obtenerPrecioProductoPorNombres/{nProducto}/{nComercio}")
   public ResponseEntity<PrecioProductoDTO> obtenerPrecioProductoPorNombres(@PathVariable String nProducto, @PathVariable String nComercio) {
        Long idPP = clienteComercio.findEspecificIDPrecioProducto(nProducto, nComercio).getBody();
        PrecioProductoDTO precioProductoDTO = new PrecioProductoDTO();
        precioProductoDTO = clienteComercio.traerProductoEspecificoPorId(idPP).getBody();

        return ResponseEntity.ok(precioProductoDTO);
   }
}

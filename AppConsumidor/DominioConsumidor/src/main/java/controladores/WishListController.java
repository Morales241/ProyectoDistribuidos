package controladores;

import convertidores.Convertidor;
import convertidores.ConvertidorCarrito;
import convertidores.ConvertidorConsumidor;
import convertidores.ConvertidorWishList;
import dtos.*;
import entidades.*;
import excepciones.ConsumidorServiciosException;
import feings.ComercioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import servicios.ConsumidorService;
import servicios.WishListService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/wishList")
public class WishListController {

    Convertidor<ConsumidorDTO, Consumidor> convertidorConsumidor = new ConvertidorConsumidor();

    Convertidor<ProductoWishListDTO, ProductoWishList> convertidorWishList = new ConvertidorWishList();

    @Autowired
    private WishListService servicio;

    @Autowired
    private ComercioClient clienteComercio;

    @Autowired
    private ConsumidorService servicioConsumidor;

    @GetMapping("/obtenerWishListPorConsumidor/{idConsumidor}")
    public ResponseEntity<List<ProductoWishListDTO>> obtenerWishlist(@PathVariable Long idConsumidor) {
        Optional<Consumidor> consumidor = servicioConsumidor.obtener(idConsumidor);
        List<ProductoWishList> productoWishLists = servicio.traerWishListPorConsumidor(consumidor.get());
        List<ProductoWishListDTO> dtos = new ArrayList<>();
        dtos = traerInfoCompleta(productoWishLists);

        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/obtenerWishListPorComercio/{comercio}")
    public ResponseEntity<List<ProductoWishListDTO>> obtenerWishlist(@PathVariable String comercio) {
        Long comercioId = clienteComercio.buscarComercioIdPorNombre(comercio).getBody();
        List<ProductoWishList> productoWishLists = servicio.traerPorComercioId(comercioId);
        List<ProductoWishListDTO> dtos = new ArrayList<>();
        dtos = traerInfoCompleta(productoWishLists);

        return ResponseEntity.ok(dtos);
    }

    @PostMapping("/guardarWishList")
    public ResponseEntity<ProductoWishListDTO> guardarWishList(@RequestBody ProductoWishListDTO dto) {
        Long comercioId = clienteComercio.buscarComercioIdPorNombre(dto.getNombreComercio()).getBody();
        Consumidor consumidor = servicioConsumidor.obtener(dto.getConsumidor()).get();

        ProductoWishList productoWishList = new ProductoWishList();
        productoWishList = convertidorWishList.convertFromDto(dto);
        productoWishList.setConsumidor(consumidor);
        productoWishList.setIdComercio(comercioId);
        productoWishList.setFecha(LocalDateTime.now());
        servicio.guardarWishList(productoWishList);

        return ResponseEntity.ok(dto);
    }

    private List<ProductoWishListDTO> traerInfoCompleta(List<ProductoWishList> productoWishLists){
        List<ProductoWishListDTO> dtos = new ArrayList<>();
        dtos = convertidorWishList.createFromEntities(productoWishLists);
        for (int i = 0; i < productoWishLists.size(); i++) {
            ComercioDTO comercioDTO = clienteComercio.obtener(productoWishLists.get(i).getIdComercio()).getBody();
            dtos.get(i).setConsumidor(productoWishLists.get(i).getConsumidor().getId());
            dtos.get(i).setNombreComercio(comercioDTO.getNombre());
        }
        return dtos;
    }

}
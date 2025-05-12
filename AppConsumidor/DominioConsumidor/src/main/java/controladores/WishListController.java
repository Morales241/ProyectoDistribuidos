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

@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5174", "http://localhost:5175"})
@RestController
@RequestMapping("/wishList")
public class WishListController {

    Convertidor<ConsumidorDTO, Consumidor> convertidorConsumidor = new ConvertidorConsumidor();

    Convertidor<WishListDTO, WishList> convertidorWishList = new ConvertidorWishList();


    @Autowired
    private WishListService servicio;

    @Autowired
    private ComercioClient clienteComercio;

    @Autowired
    private ConsumidorService servicioConsumidor;

    @PostMapping("/crearWishList/{idConsumidor}/{nombre}")
    public ResponseEntity<WishListDTO> crearWishlist(@PathVariable Long idConsumidor, @PathVariable String nombre) {
        Optional<Consumidor> consumidor = servicioConsumidor.obtener(idConsumidor);

        WishListDTO dto = new WishListDTO();
        dto.setConsumidor(convertidorConsumidor.convertFromEntity(consumidor.get()));
        dto.setNombre(nombre);
        servicio.save(convertidorWishList.convertFromDto(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @GetMapping("/obtenerTodas/{idConsumidor}")
    public ResponseEntity<List<WishListDTO>> obtenerWishlist(@PathVariable Long idConsumidor) {
        Optional<Consumidor> consumidor = servicioConsumidor.obtener(idConsumidor);

        List<WishListDTO> dtos = new ArrayList<>();
        dtos = convertidorWishList.createFromEntities(servicio.ObtenerWishListsPorConsumidor(consumidor.get().getId()));

        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }

//    @GetMapping("/obtenerEspecifica/{idConsumidor}/{nombre}")
//    public ResponseEntity<WishListDTO> obtenerWishlistEspecifica(@PathVariable Long idConsumidor, @PathVariable String nombre) {
//        Optional<Consumidor> consumidor = servicioConsumidor.obtener(idConsumidor);
//
//        WishListDTO dtos = new WishListDTO();
//        dtos = convertidorWishList.convertFromEntity(servicio.ObtenerWishListPorNombre(nombre, consumidor.get().getId()));
//
//        return ResponseEntity.ok(dtos);
//    }

//    @DeleteMapping("/eliminar/{idConsumidor}/{nombre}")
//    public ResponseEntity<Void> eliminarWishlistItem(@PathVariable Long idConsumidor, @PathVariable String nombre) {
//
//        Optional<Consumidor> consumidor = servicioConsumidor.obtener(idConsumidor);
//
//        servicio.remove(nombre, consumidor.get().getId());
//
//        return ResponseEntity.status(HttpStatus.OK).build();
//    }

//    @PostMapping("/agregarAWishList/{idConsumidor}/{nombre}")
//    public ResponseEntity<Void> agregarProducto(
//            @PathVariable Long idConsumidor,
//            @PathVariable String nombre,
//            @RequestBody ProductoWishListDTO productoDTO) {
//
//        WishList wishList = servicio.ObtenerWishListPorNombre(nombre, idConsumidor);
//        if (wishList == null) {
//            return ResponseEntity.notFound().build();
//        }
//
//        for (ProductoWishList producto : wishList.getProductos()) {
////            PrecioProductoDTO productoAux = clienteComercio.traerProductoEspecificoPorId(producto.getIdPrecioProducto()).getBody();
////            if (productoAux == null) continue;
////
////            if (productoAux.getProducto().equals(productoDTO.getProducto().getProducto()) &&
////                    productoAux.getComercio().equals(productoDTO.getProducto().getComercio())) {
////
//////                producto.setCantidad(producto.getCantidad() + productoDTO.getCantidad());
////                servicio.save(wishList);
////                return ResponseEntity.ok().build();
////            }
//        }
//
//        Long idPrecioProducto = clienteComercio
//                .traerProductoEspecifico(productoDTO.getProducto().getProducto(), productoDTO.getProducto().getComercio())
//                .getBody();
//
//        if (idPrecioProducto == null) {
//            return ResponseEntity.badRequest().build();
//        }
//
//        ProductoWishList nuevoProducto = new ProductoWishList();
////        nuevoProducto.setCantidad(productoDTO.getCantidad());
////        nuevoProducto.setWishList(wishList);
////        nuevoProducto.setIdPrecioProducto(idPrecioProducto);
//
//        wishList.getProductos().add(nuevoProducto);
//        servicio.save(wishList);
//
//        return ResponseEntity.ok().build();
//    }


//    @PostMapping("/eliminarDeWishList/{idConsumidor}/{nombre}")
//    public ResponseEntity<Void> eliminarProducto(
//            @PathVariable Long idConsumidor,
//            @PathVariable String nombre,
//            @RequestBody CarritoProductoDTO productoDTO) {
//
//        WishList wishList = servicio.ObtenerWishListPorNombre(nombre, idConsumidor);
//        if (wishList == null) {
//            return ResponseEntity.notFound().build();
//        }
//
//        Iterator<ProductoWishList> iterator = wishList.getProductos().iterator();
//        while (iterator.hasNext()) {
////            ProductoWishList producto = iterator.next();
////            PrecioProductoDTO productoAux = clienteComercio.traerProductoEspecificoPorId(producto.getIdPrecioProducto()).getBody();
////            if (productoAux == null) continue;
////
////            if (productoAux.getProducto().equals(productoDTO.getProducto().getProducto()) &&
////                    productoAux.getComercio().equals(productoDTO.getProducto().getComercio())) {
//
////                if (producto.getCantidad() <= productoDTO.getCantidad()) {
////                    iterator.remove();
////                } else {
////                    producto.setCantidad(producto.getCantidad() - productoDTO.getCantidad());
////                }
//
////                servicio.save(wishList);
////                return ResponseEntity.ok().build();
////            }
//           }
//
//        return ResponseEntity.badRequest().build();
//    }
}
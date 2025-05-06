package servicios;

import entidades.PrecioProducto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositorios.PrecioProductoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PrecioProductoService {

    @Autowired
    private PrecioProductoRepository precioProductoRepository;

    public List<PrecioProducto> findByComercioId(Long comercioId) {
        return precioProductoRepository.findByidComercio(comercioId);
    }

    public List<PrecioProducto> findByProductoId(Long productoId) {
        return precioProductoRepository.findByidProducto(productoId);
    }

    public Optional<PrecioProducto> findEspecificPrecioProducto(Long productoId, Long comercioId) {
        return precioProductoRepository.findByIdProductoAndIdComercio(productoId, comercioId);
    }

    public PrecioProducto crearPrecioProducto(PrecioProducto precioProducto) {
        return precioProductoRepository.save(precioProducto);
    }

    public void eliminarPrecioProducto(Long precioProducto) {
        precioProductoRepository.deleteById(precioProducto);
    }

    public PrecioProducto findPrecioProductoById(Long precioProductoId) {
        return precioProductoRepository.findById(precioProductoId).get();
    }
}

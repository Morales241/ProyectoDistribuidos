package servicios;

import entidades.Resena;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositorios.ResenaRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ResenaService {

    @Autowired
    private ResenaRepository resenaRepository;

    public Resena crearResena(Resena resena) {
        resena.setFecha(LocalDateTime.now());
        return resenaRepository.save(resena);
    }

    public List<Resena> obtenerPorProducto(Long idProducto) {
        return resenaRepository.findByProductoEnResena(idProducto);
    }

    public List<Resena> obtenerPorConsumidor(Long idConsumidor) {
        return resenaRepository.findByConsumidorId(idConsumidor);
    }

    public void eliminarResena(Long id) {
        resenaRepository.deleteById(id);
    }
}

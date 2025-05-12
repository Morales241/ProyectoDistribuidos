package servicios;

import entidades.Consumidor;
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

    public List<Resena> obtenerPorConsumidor(Consumidor consumidor) {
        return resenaRepository.findByConsumidor(consumidor);
    }

    public List<Resena> obtenerPorComercio(Long comercio) {
        return resenaRepository.findByIdComercio(comercio);
    }

    public void eliminarResena(Long id) {
        resenaRepository.deleteById(id);
    }
}
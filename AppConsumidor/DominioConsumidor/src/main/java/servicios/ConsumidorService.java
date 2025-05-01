package servicios;

import entidades.Consumidor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositorios.ConsumidorRepository;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class ConsumidorService {

    @Autowired
    private ConsumidorRepository consumidorRepository;

    public Consumidor registrar(Consumidor consumidor) {
        consumidor.setFechaRegistro(LocalDate.now());
        return consumidorRepository.save(consumidor);
    }

    public Optional<Consumidor> obtener(Long id) {
        return consumidorRepository.findById(id);
    }

    public Optional<Consumidor> obtenerPorCorreo(String correo) {
        return consumidorRepository.findByCorreo(correo);
    }

    public void eliminar(Long id) {
        consumidorRepository.deleteById(id);
    }
}

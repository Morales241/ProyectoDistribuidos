package servicios;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EncriptamientoService {
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public String encriptarContrasena(String password) {
        return passwordEncoder.encode(password);
    }

    public boolean verificarContrasena(String password, String hashedPassword) {
        return passwordEncoder.matches(password, hashedPassword);
    }
}

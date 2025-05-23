package servicios;

import dtos.OfertaDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificacionService {
   @Autowired
   private final SimpMessagingTemplate messagingTemplate;

    public void enviarNotificacion(List<String> idsUsuarios, OfertaDTO oferta) {
        for (String id : idsUsuarios) {
            messagingTemplate.convertAndSendToUser(
                    id,
                    "/notificaciones",
                    oferta
            );
        }
    }
}

package servicios;

import com.fasterxml.jackson.databind.ObjectMapper;
import dtos.ReporteMensajeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

@Service
public class ReportesService {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @RabbitListener(queues = "colaReportes", containerFactory = "rabbitListenerContainerFactory")
    public void recibirReporte(String json) throws Exception {
        ReporteMensajeDTO message = objectMapper.readValue(json, ReporteMensajeDTO.class);
        System.out.println("Mensaje recibido: " + message);
        messagingTemplate.convertAndSend("/topic/reportes", message);

    }


}

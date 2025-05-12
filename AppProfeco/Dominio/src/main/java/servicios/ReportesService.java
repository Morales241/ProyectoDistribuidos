package servicios;

import org.springframework.stereotype.Service;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

@Service
public class ReportesService {

    @RabbitListener(queues = {"colaReportes"})
    public void recibirReporte(String message){

    }
}

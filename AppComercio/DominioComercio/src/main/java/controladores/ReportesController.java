package controladores;

import feigns.ConsumidorClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ReportesController {

    @Autowired
    private ConsumidorClient clienteConsumidor;
}

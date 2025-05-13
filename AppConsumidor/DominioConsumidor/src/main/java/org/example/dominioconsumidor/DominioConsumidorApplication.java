package org.example.dominioconsumidor;

import controladores.CarritoController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = { "controladores", "servicios", "config"})
@EntityScan("entidades")
@EnableJpaRepositories("repositorios")
@EnableFeignClients("feings")
public class DominioConsumidorApplication {

    public static void main(String[] args) {
        SpringApplication.run(DominioConsumidorApplication.class, args);
    }

}

package org.example.dominiocomercio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = { "controladores, servicios" })
@EntityScan("entidades")
@EnableJpaRepositories("repositorios")
public class DominioComercioApplication {

    public static void main(String[] args) {
        SpringApplication.run(DominioComercioApplication.class, args);
    }

}

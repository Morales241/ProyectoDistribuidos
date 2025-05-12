package config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Bean
    public Queue cola(){
        return new Queue("colaReportes");
    }

    // spring bean for rabbitmq exchange
    @Bean
    public TopicExchange exchange(){
        return new TopicExchange("reportes");
    }

    @Bean
    public Binding binding(){
        return BindingBuilder
                .bind(cola())
                .to(exchange())
                .with("reportesKey");
    }
}

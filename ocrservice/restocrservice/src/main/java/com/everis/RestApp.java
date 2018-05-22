package com.everis;

import com.everis.businesslogic.interfaces.IUserControl;
import com.everis.security.JwtFilter;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
@ComponentScan({"com.everis.*"})
@EntityScan({"com.everis.*"})
public class RestApp {

    public final  static String TASK_QUEUE_NAME = "OCRQueue";


    @Autowired
    private IUserControl iUserControl;

    @Bean
    Queue queue() {
        return new Queue(TASK_QUEUE_NAME, false);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange("spring-boot-exchange");
    }

    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(TASK_QUEUE_NAME);
    }


    @Bean
    public FilterRegistrationBean jwtFilter() {
        final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new JwtFilter(iUserControl));
        registrationBean.addUrlPatterns("/secure/*");

        return registrationBean;
    }

    public static void main(String[] args) {
        SpringApplication.run(RestApp.class, args);
    }
}


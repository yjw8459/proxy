package hello.proxy.config;

import hello.proxy.app.v1.*;
import hello.proxy.self.OrderController;
import hello.proxy.self.OrderProxy;
import hello.proxy.self.OrderService;
import hello.proxy.self.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SelfConfig {

    @Bean
    public OrderController orderController(){
        return new OrderController(orderService());
    }

    @Bean
    public OrderService orderService(){
        return new OrderProxy(new OrderServiceImpl());
    }

}

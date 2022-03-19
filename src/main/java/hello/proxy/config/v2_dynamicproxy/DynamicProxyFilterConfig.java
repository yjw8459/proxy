package hello.proxy.config.v2_dynamicproxy;

import hello.proxy.app.v1.*;
import hello.proxy.trace.logtrace.LogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Proxy;

@Configuration
public class DynamicProxyFilterConfig {

    private static final String[] PATTERNS = {"request", "order*", "save*"};

    @Bean
    public OrderControllerV1 orderController(LogTrace trace){
        return (OrderControllerV1)Proxy.newProxyInstance(
                OrderControllerV1.class.getClassLoader(),
                new Class[]{OrderControllerV1.class},
                new LogTraceFilterHandler(new OrderControllerV1Impl(orderService(trace)), trace, PATTERNS));
    }

    @Bean
    public OrderServiceV1 orderService(LogTrace trace){
        return (OrderServiceV1) Proxy.newProxyInstance(
                OrderServiceV1.class.getClassLoader(),
                new Class[]{OrderServiceV1.class},
                new LogTraceFilterHandler(new OrderServiceV1Impl(orderRepository(trace)), trace, PATTERNS));
    }

    @Bean
    public OrderRepositoryV1 orderRepository(LogTrace trace){
        return (OrderRepositoryV1) Proxy.newProxyInstance(
                OrderRepositoryV1.class.getClassLoader(),
                new Class[]{OrderRepositoryV1.class},
                new LogTraceFilterHandler(new OrderRepositoryV1Impl(), trace, PATTERNS));
    }
}

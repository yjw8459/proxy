package hello.proxy.config.v1_proxy;

import hello.proxy.app.v1.*;
import hello.proxy.config.v1_proxy.interface_proxy.OrderControllerInterfaceProxy;
import hello.proxy.config.v1_proxy.interface_proxy.OrderRepositoryInterfaceProxy;
import hello.proxy.config.v1_proxy.interface_proxy.OrderServiceInterfaceProxy;
import hello.proxy.trace.logtrace.LogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 프록시 조립
 */
@Configuration
public class InterfaceProxyConfig {

    /**
     * 컨트롤러는 Proxy를 생성한다.
     * 즉, 스프링 빈에 Proxy가 등록된다.
     * 그리고 Controller가 의존하는 Service도 Proxy로 주입한다.
     * @param trace
     * @return
     */
    @Bean
    public OrderControllerV1 orderController(LogTrace trace){
        //구현체를 반환하지 않고,
        //return new OrderControllerV1Impl(orderService());
        //프록시 객체를 반환한다.
        return new OrderControllerInterfaceProxy(new OrderControllerV1Impl(orderService(trace)), trace);
    }

    @Bean
    public OrderServiceV1 orderService(LogTrace trace){
        return new OrderServiceInterfaceProxy(new OrderServiceV1Impl(orderRepository(trace)), trace);
    }

    @Bean
    public OrderRepositoryV1 orderRepository(LogTrace trace){
        return new OrderRepositoryInterfaceProxy(new OrderRepositoryV1Impl(), trace);
    }
}

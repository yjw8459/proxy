package hello.proxy.app.v3;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service    //컴포넌트 스캔 대상
public class OrderServiceV3 {

    private final OrderRepositoryV3 orderRepository;

    public OrderServiceV3(OrderRepositoryV3 orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void orderItem(String itemId){
        orderRepository.save(itemId);
    }
}

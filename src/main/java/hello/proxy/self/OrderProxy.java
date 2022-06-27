package hello.proxy.self;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class OrderProxy implements OrderService{

    private final OrderService target;

    @Override
    public String orderItem(String itemId) {
        log.info("Proxy호출");
        return target.orderItem(itemId);
    }
}

package hello.proxy.self;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService{
    @Override
    public String orderItem(String itemId) {
      log.info("Service Process");
      return "data : " + itemId;
    }
}

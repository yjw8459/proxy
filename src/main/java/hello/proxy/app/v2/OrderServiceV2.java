package hello.proxy.app.v2;


public class OrderServiceV2 {

    private final OrderRespositoryV2 orderRepository;

    public OrderServiceV2(OrderRespositoryV2 orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void orderItem(String itemId) {
        orderRepository.save(itemId);
    }
}

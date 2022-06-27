package hello.proxy.config.v1_proxy.concrete_proxy;

import hello.proxy.app.v2.OrderRepositoryV2;
import hello.proxy.app.v2.OrderServiceV2;
import hello.proxy.trace.TraceStatus;
import hello.proxy.trace.logtrace.LogTrace;

public class OrderServiceConcreteProxy extends OrderServiceV2 {

    private final OrderServiceV2 target;
    private final LogTrace trace;

    public OrderServiceConcreteProxy(OrderServiceV2 target, LogTrace trace) {
        super(null);    //상위 기능을 쓰지 않고 프록시로만 사용하기 때문에 super(null)을 사용한다.
        this.target = target;
        this.trace = trace;
    }

    @Override
    public void orderItem(String itemId){
        TraceStatus status = null;
        try {
            status = trace.begin("OrderService.orderItem()");
            //target 호출
            target.orderItem(itemId);
            trace.end(status);
        }catch (Exception e){
            trace.exception(status, e);
            e.printStackTrace();
        }
    }
}

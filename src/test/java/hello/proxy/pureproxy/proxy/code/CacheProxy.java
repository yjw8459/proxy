package hello.proxy.pureproxy.proxy.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CacheProxy implements Subject{

    private Subject target;     //프록시가 호출되는 대상
    private String cacheValue;

    public CacheProxy(Subject target) {
        this.target = target;
    }

    /**
     * 클라이언트가 프록시를 호출하면 프록시가 최종적으로 실제 객체를 호출해야 한다.
     * 따라서 내부에 실제 객체의 참조를 가지고 있어야 한다.
     * 이렇게 프록시가 호출하는 대상을 target이라 한다.
     *
     * 처음 호출 이후에 매우 빠르게 호출할 수 있음.
     * @return
     */
    @Override
    public String operation() {
        log.info("프록시 호출");
        if ( cacheValue == null )   //cacheValue에 값이 없으면 실제 객체 호출
            cacheValue = target.operation();
        return cacheValue;
    }
}

package hello.proxy.pureproxy.proxy;

import hello.proxy.pureproxy.proxy.code.CacheProxy;
import hello.proxy.pureproxy.proxy.code.ProxyPatternClient;
import hello.proxy.pureproxy.proxy.code.RealSubject;
import org.junit.jupiter.api.Test;

public class ProxyPattrernTest {

    @Test
    void noProxyTest(){
        RealSubject realSubject = new RealSubject();
        ProxyPatternClient client = new ProxyPatternClient(realSubject);

        //client 3번 호출, 3초 소요
        client.execute();
        client.execute();
        client.execute();

    }

    /**
     * Cache Proxy 사용
     * CacheProxy에서 데이터가 없을 경우 RealSubject를 사용하고,
     * 데이터가 있을 경우 Proxy에서 바로 반환
     */
    @Test
    void cacheProxyTest(){
        RealSubject realSubject = new RealSubject();
        CacheProxy cacheProxy = new CacheProxy(realSubject);
        ProxyPatternClient client = new ProxyPatternClient(cacheProxy);

        //client 3번 호출, 1초 소모
        client.execute();
        client.execute();
        client.execute();
    }
}

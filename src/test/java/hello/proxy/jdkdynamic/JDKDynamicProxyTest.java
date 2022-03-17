package hello.proxy.jdkdynamic;

import hello.proxy.jdkdynamic.code.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;

@Slf4j
public class JDKDynamicProxyTest {

    @Test
    void dynamicA(){
        AInterface target = new AInterfaceImpl();   //프록시 넣고 싶은 target
        TimeInvocationHandler handler = new TimeInvocationHandler(target);//프록시를 호출할 로직

        AInterface proxy = (AInterface)Proxy.newProxyInstance(//Object를 반환하기 때문에 캐스팅
                AInterface.class.getClassLoader(),//어느 클래스 로더에 할지
                new Class[]{AInterface.class},//인터페이스를 여러 개 구현할 수 있음
                handler
        );//프록시 생성
        proxy.call();   //호출 시 handler.invoke 호출
        log.info("targetClass={}", target.getClass());
        log.info("proxyClass={}", proxy.getClass());
    }

    @Test
    void dynamicB(){
        BInterface target = new BInterfaceImpl();   //프록시 넣고 싶은 target
        TimeInvocationHandler handler = new TimeInvocationHandler(target);//프록시를 호출할 로직

        BInterface proxy = (BInterface)Proxy.newProxyInstance(//Object를 반환하기 때문에 캐스팅
                BInterface.class.getClassLoader(),//어느 클래스 로더에 할지
                new Class[]{BInterface.class},//인터페이스를 여러 개 구현할 수 있음
                handler
        );
        proxy.call();   //호출 시 handler.invoke 호출
        log.info("targetClass={}", target.getClass());
        log.info("proxyClass={}", proxy.getClass());
    }
}

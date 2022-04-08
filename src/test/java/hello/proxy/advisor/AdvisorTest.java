package hello.proxy.advisor;

import hello.proxy.pureproxy.proxy.common.ServiceImpl;
import hello.proxy.pureproxy.proxy.common.ServiceInterface;
import hello.proxy.pureproxy.proxy.common.advice.TimeAdvice;
import org.junit.jupiter.api.Test;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;

public class AdvisorTest {

    /**
     * new DefaultPointcutAdvisor : Advisor 인터페이스의 가장 일반적인 구현체이다.
     *                              생성자를 통해 하나의 포인트컷과 하나의 어드바이스를 넣어주면 된다.
     */
    @Test
    void advisorTest1(){
        ServiceInterface target = new ServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(Pointcut.TRUE, new TimeAdvice());
        proxyFactory.addAdvisor(advisor);
        ServiceInterface proxy = (ServiceInterface)proxyFactory.getProxy();

        proxy.save();
        proxy.find();
    }
}

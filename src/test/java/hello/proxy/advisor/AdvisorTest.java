package hello.proxy.advisor;

import hello.proxy.pureproxy.proxy.common.ServiceImpl;
import hello.proxy.pureproxy.proxy.common.ServiceInterface;
import hello.proxy.pureproxy.proxy.common.advice.TimeAdvice;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;

import java.lang.reflect.Method;

public class AdvisorTest {

    /**
     * new DefaultPointcutAdvisor : Advisor 인터페이스의 가장 일반적인 구현체이다.
     *                              생성자를 통해 하나의 포인트컷과 하나의 어드바이스를 넣어주면 된다.
     * proxyFactory.addAdvisor(advisor) : 어디에 어떤 부가 기능을 적용해야 할지 어드바이스 하나로 알 수 있다.
     *
     * Pointcut.TRUE : 항상 차밍 포인트 값.
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

    @Test
    @DisplayName("직접 만든 포인트컷")
    void advisorTest2(){
        ServiceInterface target = new ServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(new MyPointcut(), new TimeAdvice());
        proxyFactory.addAdvisor(advisor);
        ServiceInterface proxy = (ServiceInterface)proxyFactory.getProxy();

        proxy.save();
        proxy.find();
    }

    @Test
    @DisplayName("스프링이 제공하는 포인트컷")
    void advisorTest3(){
        ServiceInterface target = new ServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);

        //스프링 제공 포인트컷
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedNames("save");

        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(pointcut, new TimeAdvice());
        proxyFactory.addAdvisor(advisor);
        ServiceInterface proxy = (ServiceInterface)proxyFactory.getProxy();

        proxy.save();
        proxy.find();
    }

    @Slf4j
    static class MyPointcut implements Pointcut{

        @Override
        public ClassFilter getClassFilter() {
            log.info("getClassFilter");
            return ClassFilter.TRUE;//항상 TRUE를 반환한다.
        }

        @Override
        public MethodMatcher getMethodMatcher() {
            log.info("getMethodMatcher");
            return new MyMethodMacher();
        }
    }

    @Slf4j
    static class MyMethodMacher implements MethodMatcher{

        private String matchName = "save";

        //method, targetClass 정보가 넘어온다. 이 정보로 어드바이스를 적용할지 안할지 판단한다.
        @Override
        public boolean matches(Method method, Class<?> targetClass) {

            boolean result = method.getName().equals("save");
            log.info("Pointcut. 호출 method={}, targetClass={}", method.getName(), targetClass);
            log.info("포인트 컷의 결과 result={}", result);
            return result;
        }

        /**
         * isRuntime이 false인 경우 클래스의 정적 정보만 사용하기 떄문에 스프링이 내부에 캐싱을 통해 성능 향상이 가능하지만
         * true인 경우 매개변수가 동적으로 변경된다고 가정하기 때문에 캐싱을 하지 않는다.
         */
        @Override
        public boolean isRuntime() {
            return false;
        }

        @Override
        public boolean matches(Method method, Class<?> targetClass, Object... args) {
            return false;
        }
    }
}

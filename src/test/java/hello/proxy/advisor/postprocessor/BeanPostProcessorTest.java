package hello.proxy.advisor.postprocessor;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BeanPostProcessorTest {

    @Test
    void basicConfig(){
        //스프링 빈으로 BasicConfig가 스프링 빈으로 등록되고, 등록되면서 BasicConfig가 호출되고 Bean을 생성한다.
        ApplicationContext context = new AnnotationConfigApplicationContext(BeanPostProcessorConfig.class);//스프링 컨테이너

        //A는 빈으로 등록된다.
        B b = context.getBean("beanA", B.class);
        b.helloB();

        B b2 = context.getBean("beanB", B.class);
        b2.helloB();

        //B는 빈으로 등록되지 않는다.
        Assertions.assertThrows(NoSuchBeanDefinitionException.class, () -> context.getBean(B.class));
    }

    @Slf4j
    @Configuration
    static class BeanPostProcessorConfig{
        @Bean(name = "beanA")
        public A a(){
            return new A();
        }

        @Bean(name = "beanB")
        public B b(){
            return new B();
        }

        @Bean//빈 후처리기를 사용하기 위해선 빈후처리기를 스프링 빈으로 등록해야한다.
        public AToBPostProcessor helloPostProcessor(){
            return new AToBPostProcessor();
        }
    }

    @Slf4j
    static class A{
        public void helloA(){
            log.info("hello A");
        }
    }

    @Slf4j
    static class B{
        public void helloB(){
            log.info("hello B");
        }
    }

    @Slf4j//default로 되어있으면 기본 로직이 구현되어 있기 때문에 interface의 메서드를 필수로 구현하지 않아도 된다.
    static class AToBPostProcessor implements BeanPostProcessor{

        @Override//객체와 이름 전달
        public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
            log.info("beanName={} bean={}", beanName, bean);
            if (bean instanceof A) return new B();
            return bean;
        }
    }

    //interface에 default 선언을 통해서 기본 로직을 구현하여 인터페이스 구현 시 필수로 오버라이드 하지 않아도 된다.
    interface test{

        default void test1() {
            System.out.println("test1");
        }

        default void test2(){
            System.out.println("test2");
        }
    }

}

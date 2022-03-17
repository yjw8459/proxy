package hello.proxy.jdkdynamic;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

@Slf4j
public class ReflectionTest {

    @Test
    public void reflection0(){
        Hello target = new Hello();

        //공통 로직1 시작
        log.info("start");
        String result1 = target.callA();
        log.info("result={}", result1);
        //공통 로직2 시작
        String result2 = target.callB();
        log.info("result={}", result2);
    }

    @Test
    void reflection1() throws Exception {
        //클래스 정보, 내부 클래스는 구분을 위해 $를 사용한다.
        Class classHello = Class.forName("hello.proxy.jdkdynamic.ReflectionTest$Hello");

        Hello target = new Hello();
        //callA 메서드 정보
        Method methodCallA = classHello.getMethod("callA");// callA의 메서드 정보(메타 정보)
        Object result1 = methodCallA.invoke(target);//target(인스턴스)의 methodCallA(메타정보)를 실행해라
        log.info("result1={}", result1);

        Method methodCallB = classHello.getMethod("callB");
        Object result2 = methodCallB.invoke(target);
        log.info("result2={}", result2);

    }

    @Test
    void reflection2() throws Exception {
        //클래스 정보, 내부 클래스는 구분을 위해 $를 사용한다.
        Class classHello = Class.forName("hello.proxy.jdkdynamic.ReflectionTest$Hello");

        Hello target = new Hello();
        //callA 메서드 정보
        Method methodCallA = classHello.getMethod("callA");//callA의 메서드 정보(메타 정보)
        dynamicCall(methodCallA, target);

        Method methodCallB = classHello.getMethod("callB");
        dynamicCall(methodCallB, target);

    }

    //Method 메타정보를 통해서 공통 로직을 작성할 수있다.
    private void dynamicCall(Method method, Object target) throws Exception{
        log.info("start");
        Object result = method.invoke(target);
        log.info("result={}", result);
        log.info("end");
    }

    @Slf4j
    static class Hello{
        public String callA(){
            log.info("call A");
            return "A";
        }
        public String callB(){
            log.info("call B");
            return "B";
        }
    }
}

package hello.proxy.pureproxy.proxy.cglib.code;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

@Slf4j
public class TimeMethodInterceptor implements MethodInterceptor {

    private final Object target;

    public TimeMethodInterceptor(Object target) {
        this.target = target;
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        log.info("TimeProxy 실핼");
        long startTime = System.currentTimeMillis();
        log.info("args={}", args);

        Object result = methodProxy.invoke(target, args);//methodProxy를 사용하면 조금 더 빠르다고 한다.
        //Object result = method.invoke(target, args);//실행, 동적 프록시가 실행할 로직

        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("TimeProxy 종료 resultTime={}", resultTime);
        return result;
    }
}

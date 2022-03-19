package hello.proxy.pureproxy.proxy.common.advice;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;

@Slf4j
public class TimeAdvice implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        log.info("TimeProxy 실핼");
        long startTime = System.currentTimeMillis();
//        Object result = method.invoke(target, args); 기존 Method.invoke 대신
//        Method method = invocation.getMethod();  MethodInvocation.getMethod.invoke();를 사용한다.

        /**
         * 혹은 invoke대신 proceed로 간편하게 사용한다.
         * 알아서 target을 찾아서 target의 실제 메서드를 호출하고 그 결과를 받는다.
         */
        Object result = invocation.proceed();

        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("TimeProxy 종료 resultTime={}", resultTime);
        return result;
    }
}

package hello.proxy.config.v2_dynamicproxy.handler;

import hello.proxy.trace.TraceStatus;
import hello.proxy.trace.logtrace.LogTrace;
import org.springframework.util.PatternMatchUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class LogTraceFilterHandler implements InvocationHandler {

    private final Object target;
    private final LogTrace trace;
    private final String[] patterns; //패턴일 땐 로그 남기기

    public LogTraceFilterHandler(Object target, LogTrace trace, String[] patterns) {
        this.target = target;
        this.trace = trace;
        this.patterns = patterns;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        //메서드 필터
        String methodName = method.getName();
        //save, request, *est
        if ( !PatternMatchUtils.simpleMatch(patterns, methodName)){
            return method.invoke(target, args);
        }


        TraceStatus status = null;
        try {
            String message = method.getDeclaringClass().getSimpleName() + "." +
                    method.getName() + "()";
            status = trace.begin(message);
            //target 호출
            Object result = method.invoke(target, args);
            trace.end(status);
            return result;
        }catch (Exception e){
            trace.exception(status, e);
            throw e;
        }
    }
}

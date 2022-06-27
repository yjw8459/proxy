package hello.proxy.config.v6_aop.aspect;

import hello.proxy.trace.TraceStatus;
import hello.proxy.trace.logtrace.LogTrace;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import java.lang.reflect.Method;

@Slf4j
@Aspect//애노테이션 기반 프록시를 적용할 때 필요하다.
public class LogTraceAspect {

    private final LogTrace logTrace;

    public LogTraceAspect(LogTrace logTrace){
        this.logTrace = logTrace;
    }

    /**
     * 어드바이저 Advisor
     *
     * 그 외
     * joinPoint.getTarget();    실제 호출 대상
     * joinPoint.getArgs();      전달 인자
     * joinPoint.getSignature(); joinPoint 시그니처
     *
     * Around에 pointcut 조건(표현식)을 넣으면 된다.
     * 표현식은 AspectJ 표현식을 사용한다.
     *
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("execution(* hello.proxy.app..*(..))")//Pointcut
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable{
        //Advice
        TraceStatus status = null;
        try {
            String message = joinPoint.getSignature().toString();
            status = logTrace.begin(message);
            //target 호출
            Object result = joinPoint.proceed();//실제 호출 대상(target)을 호출한다.
            logTrace.end(status);
            return result;
        }catch (Exception e){
            logTrace.exception(status, e);
            throw e;
        }
    }
}

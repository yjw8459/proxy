package hello.proxy.config.v5_autoproxy;

import hello.proxy.config.AppV1Config;
import hello.proxy.config.AppV2Config;
import hello.proxy.config.v3_proxyfactory.advice.LogTraceAdvice;
import hello.proxy.trace.logtrace.LogTrace;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({AppV1Config.class, AppV2Config.class})
public class AutoProxyConfig {

    //@Bean
    //의존성으로 spring.aop를 등록하면 AnnotationAwareAspectJAutoProxyCreator를 통해 BeanPostProcessor를 알아서 처리해줌.
    public Advisor advisor1(LogTrace logTrace){
        //pointcut
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedNames("request*", "order*", "save*");
        //advise
        LogTraceAdvice advice = new LogTraceAdvice(logTrace);
        return new DefaultPointcutAdvisor(pointcut, advice);//포인트 컷, 어드바이스 적용
    }

    @Bean
    public Advisor advisor2(LogTrace logTrace){
        //pointcut
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        // app..* : app 패키지와 하위의 모든 패키지.
        // *(..) : 모든 메서드 이름,
        // (..) : 파라미터 갯수 상관X
        pointcut.setExpression("execution(* hello.proxy.app..*(..))");
        LogTraceAdvice advice = new LogTraceAdvice(logTrace);
        return new DefaultPointcutAdvisor(pointcut, advice);
    }

    @Bean
    public Advisor advisor3(LogTrace logTrace){
        //pointcut
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        // app..* : app 패키지와 하위의 모든 패키지.
        // *(..) : 모든 메서드 이름,
        // (..) : 파라미터 갯수 상관X
        //!execute(* hello.proxy.app..noLog(..)) : 메서드 명이 noLog가 아닌 것만
        pointcut.setExpression("execution(* hello.proxy.app..*(..)) && !execute(* hello.proxy.app..noLog(..))");
        LogTraceAdvice advice = new LogTraceAdvice(logTrace);
        return new DefaultPointcutAdvisor(pointcut, advice);
    }

}

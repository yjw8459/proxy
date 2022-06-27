package hello.proxy.config.v6_aop;

import hello.proxy.config.AppV1Config;
import hello.proxy.config.AppV2Config;
import hello.proxy.config.v6_aop.aspect.LogTraceAspect;
import hello.proxy.trace.logtrace.LogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({AppV1Config.class, AppV2Config.class})
public class AopConfig {

    //프록시를 Bean으로 등록하면 스프링 자동 빈 후처리기를 통해서 호출 빈을 포인트컷으로 체크해서 Advice를 수행한다.
    @Bean
    public LogTraceAspect logTraceAspect(LogTrace logTrace){
        return new LogTraceAspect(logTrace);
    }
}

package hello.proxy.pureproxy.concreteproxy.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TimeProxy extends ConcreteLogic {
    private ConcreteLogic concreateLogic;

    public TimeProxy(ConcreteLogic concreateLogic) {
        this.concreateLogic = concreateLogic;
    }

    @Override
    public String operation(){
        log.info("TimeDecorator 실행");
        long startTime = System.currentTimeMillis();

        String result = concreateLogic.operation();

        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("TimeDecorator 종료 resultTome={}ms", resultTime);
        return result;
    }
}

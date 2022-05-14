package platform.aop;

import common.bean.base.BizException;
import common.bean.base.Result;
import common.bean.base.StatusCode;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;


@Aspect
@Component
@Order(1)
public class ResultAspect {
    private static Logger log = LoggerFactory.getLogger(ResultAspect.class);

    @Pointcut("execution(public common.bean.base.Result *(..))")
    public void exceptionPointcut() {

    }

    @Around(value = "exceptionPointcut()")
    public Object aroundMethod(ProceedingJoinPoint proceedingJoinPoint) {
        try {
            return proceedingJoinPoint.proceed();
        } catch (BizException ex) {
            log.error("execute error. ", ex);
            return Result.fail(ex.getCode(), ex.getMsg());
        } catch (Throwable th) {
            log.error("unkonwn error. ", th);
            return Result.fail(StatusCode.UNKNOWN);
        }
    }
}

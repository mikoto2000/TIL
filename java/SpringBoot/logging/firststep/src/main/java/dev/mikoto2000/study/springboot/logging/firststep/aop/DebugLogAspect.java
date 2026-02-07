package dev.mikoto2000.study.springboot.logging.firststep.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DebugLogAspect {

    private static final Logger log = LoggerFactory.getLogger(DebugLogAspect.class);

    @Around(
      "within(dev.mikoto2000.study.springboot.logging.firststep.service..*)"
      + " || within(dev.mikoto2000.study.springboot.logging.firststep.controller..*)"
    )
    public Object logMethod(ProceedingJoinPoint pjp) throws Throwable {

        String className = pjp.getTarget().getClass().getSimpleName();

        String methodName = pjp.getSignature().getName();

        log.debug("START {}.{}", className, methodName);

        try {
            Object result = pjp.proceed();

            log.debug("END   {}.{}", className, methodName);

            return result;

        } catch (Throwable e) {

            log.debug("✖ ERROR {}.{}", className, methodName);

            throw e;
        }
    }
}


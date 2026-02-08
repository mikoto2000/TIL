package dev.mikoto2000.study.springboot.logging.firststep.aop;

import static net.logstash.logback.argument.StructuredArguments.*;

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

        log.debug("START",
            kv("event", "METHOD_START"),
            kv("className", className),
            kv("methodName", methodName));

        try {
            Object result = pjp.proceed();

            log.debug("END",
                kv("event", "METHOD_END"),
                kv("className", className),
                kv("methodName", methodName));

            return result;

        } catch (Throwable e) {

            log.debug("ERROR",
                kv("event", "METHOD_ERROR"),
                kv("className", className),
                kv("methodName", methodName));

            throw e;
        }
    }
}


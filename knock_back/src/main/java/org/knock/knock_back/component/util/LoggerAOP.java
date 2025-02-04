package org.knock.knock_back.component.util;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StopWatch;

@Aspect
@Configuration
public class LoggerAOP {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Around("execution(* org.knock.knock_back.controller..*(..))")
    public Object ControllerTimerLogger(ProceedingJoinPoint joinPoint) throws Throwable {

        logger.info("Entering ControllerTimerLogger");

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        Object result = joinPoint.proceed();

        stopWatch.stop();

        logger.info("{} took {} ms", joinPoint.getSignature().getName(), stopWatch.getTotalTimeMillis());

        logger.info("Exiting ControllerTimerLogger");

        return result;
    }

    @Around("execution(* org.knock.knock_back.service..*(..))")
    public Object ServiceTimerLogger(ProceedingJoinPoint joinPoint) throws Throwable {

        logger.info("Entering ServiceTimerLogger");

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        Object result = joinPoint.proceed();

        stopWatch.stop();

        logger.info("{} took {} ms", joinPoint.getSignature().getName(), stopWatch.getTotalTimeMillis());

        logger.info("Exiting ServiceTimerLogger");

        return result;
    }
}

package com.CStudy.domain.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class TimeTraceAop {

    @Around("@annotation(TimeAnnotation)")
    public Object excite(ProceedingJoinPoint joinPoint)throws Throwable {
        long start = System.currentTimeMillis();
        log.info("start: "+ joinPoint.toString()+" "+start+"MS");
        try {
            return joinPoint.proceed();
        }finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish-start;
            log.info("execute: "+ joinPoint.toString()+" "+timeMs+"MS");

        }
    }

}

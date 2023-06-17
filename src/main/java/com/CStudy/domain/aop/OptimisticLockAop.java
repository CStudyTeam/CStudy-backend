package com.CStudy.domain.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

import javax.persistence.OptimisticLockException;

/**
 * 낙관적 락은 데이터 충돌을 방지하기 위한 메커니즘으로 데이터 접근 이전에 락을 확보하고 충돌을 감지하고
 * 처리하기 위해 낙관적 락 관련 코드가 트랜잭션보다 먼저 실행되어야 한다. 이렇게 함으로써 충돌을 최소화하고
 * 데이터의 일관성과 정확성을 보장할 수 있다.
 */
@Slf4j
@Order(Ordered.LOWEST_PRECEDENCE - 1)
@Aspect
public class OptimisticLockAop {

    @Value("${retry.count}")
    public Integer retryMaxCount;
    @Value("${retry.sleep}")
    public Integer retryInterval;


    @Around("@annotation(OptimisticAnnotation)")
    public Object RetryTransactionIfOptimisticLockExceptionThrow(
            ProceedingJoinPoint joinPoint) throws Throwable {
        Exception exceptionHolder = null;
        for (int retryCount = 0; retryCount <= retryMaxCount; retryCount++) {
            try {
                log.info("[RETRY_COUNT]: {}", retryCount);
                return joinPoint.proceed();
            } catch (OptimisticLockException | ObjectOptimisticLockingFailureException | CannotAcquireLockException e) {
                log.error("{} 발생", e.getClass());
                exceptionHolder = (Exception) e;
                Thread.sleep(retryInterval);
            }
        }
        throw exceptionHolder;
    }

}
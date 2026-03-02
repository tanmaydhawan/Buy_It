package com.tanmay.buyit.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import com.tanmay.buyit.aspect.LogExecution;

@Aspect
@Component
@Slf4j
public class PerformanceLoggingAspect {

    @Pointcut("@annotation(LogExecution)")
    public void logExecutionAnnotation(){}

    @Around("logExecutionAnnotation()")
    public Object calculateExecutionTime (ProceedingJoinPoint joinPoint) throws Throwable {

        long start = System.currentTimeMillis();

        try{

            Object result = joinPoint.proceed();

            long executionTime = System.currentTimeMillis()-start;

            log.info("SUCCESS -> Method : {} | Execution Time : {} ms",
                    joinPoint.getSignature(),
                    executionTime);

            return result;

        } catch (Exception ex) {

            long executionTime = System.currentTimeMillis()-start;

            log.info("FAILURE -> Method : {} | Execution Time : {} ms | Error : {}",
                    joinPoint.getSignature(),
                    executionTime,
                    ex.getMessage());
            throw ex;
        }
    }
}

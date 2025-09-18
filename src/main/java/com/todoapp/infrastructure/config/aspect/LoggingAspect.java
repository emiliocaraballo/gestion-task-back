package com.todoapp.infrastructure.config.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Around("execution(* com.todoapp.infrastructure.adapter.rest.TodoRestAdapter.*(..)) || " +
           "execution(* com.todoapp.application.service.*.*(..))")
    public Object logMethodExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        
        logger.debug("Iniciando {} en la clase {}", methodName, className);
        long startTime = System.currentTimeMillis();
        
        try {
            Object result = joinPoint.proceed();
            long endTime = System.currentTimeMillis();
            logger.debug("Finalizado {} en la clase {}. Tiempo de ejecución: {}ms", 
                methodName, className, (endTime - startTime));
            return result;
        } catch (Exception e) {
            logger.error("Error en {} de la clase {}: {}", 
                methodName, className, e.getMessage(), e);
            throw e;
        }
    }
}

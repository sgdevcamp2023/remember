package harmony.communityservice.common.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class LogAop {

    @Pointcut("execution(* harmony.communityservice.community.command.service.impl..*.*(..))")
    public void commandService() {
    }

    @Pointcut("execution(* harmony.communityservice.community.command.repository.impl..*.*(..))")
    public void commandRepository() {
    }


    @Pointcut("execution(* harmony.communityservice.community.query.service.impl..*.*(..))")
    public void queryService() {
    }

    @Pointcut("execution(* harmony.communityservice.community.query.repository.impl..*.*(..))")
    public void queryRepository() {
    }

    @Around("commandService()||commandRepository()||queryService()||queryRepository()")
    public Object logging(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            return joinPoint.proceed();
        } finally {
            Signature signature = joinPoint.getSignature();
            log.info(signature.getName());
        }
    }
}





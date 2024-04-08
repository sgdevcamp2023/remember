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

    @Pointcut("execution(* harmony.communityservice.board.board.service.command.impl..*.*(..))")
    public void commandService() {
    }

    @Pointcut("execution(* harmony.communityservice.board.board.repository.command.impl..*.*(..))")
    public void commandRepository() {
    }


    @Pointcut("execution(* harmony.communityservice.board.board.service.query.impl..*.*(..))")
    public void queryService() {
    }

    @Pointcut("execution(* harmony.communityservice.board.board.repository.query.impl..*.*(..))")
    public void queryRepository() {
    }

    @Around("commandService()||commandRepository()||queryService()||queryRepository()")
    public Object logging(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            return joinPoint.proceed();
        } finally {
            Signature signature = joinPoint.getSignature();
            String className = joinPoint.getTarget().getClass().getSimpleName();
            log.info(className + "-" + signature.getName());
        }
    }
}





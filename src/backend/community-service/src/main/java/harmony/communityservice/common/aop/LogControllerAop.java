package harmony.communityservice.common.aop;

import harmony.communityservice.common.utils.InfoLogPrinter;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;


@Aspect
@Component
@RequiredArgsConstructor
public class LogControllerAop {

    private final InfoLogPrinter infoLogPrinter;

    @Pointcut("execution(* harmony.communityservice.community.command.controller..*.*(..))")
    public void commandController() {
    }

    @Pointcut("execution(* harmony.communityservice.community.query.controller..*.*(..))")
    public void queryController() {
    }


    @Around("commandController()||queryController()")
    public Object logging(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            return joinPoint.proceed();
        } finally {
            Signature signature = joinPoint.getSignature();
            String className = joinPoint.getTarget().getClass().getSimpleName();
            infoLogPrinter.logging(className + "-" + signature.getName());
        }
    }
}

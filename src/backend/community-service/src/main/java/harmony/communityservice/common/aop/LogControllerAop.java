package harmony.communityservice.common.aop;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class LogControllerAop {
    @Pointcut("execution(* harmony.communityservice.community.command.controller..*.*(..))")
    public void commandController() {
    }

    @Pointcut("execution(* harmony.communityservice.community.query.controller..*.*(..))")
    public void queryController() {
    }


    @Around("commandController()||queryController()")
    public Object logging(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        Signature signature = joinPoint.getSignature();
        String apiAddr = request.getRequestURI();
        String methodName = signature.getName();
        String traceId = request.getHeader("trace-id");
        String userId = request.getHeader("user-id");
        String httpMethod = request.getMethod();
        MDC.put("Trace-Id", traceId);
        MDC.put("Api-Addr", apiAddr);
        MDC.put("Http-Method", httpMethod);
        MDC.put("User-Id", userId);
        try {
            return joinPoint.proceed();
        } finally {
            log.info(methodName);
            MDC.remove("Trace-Id");
            MDC.remove("Api-Addr");
            MDC.remove("Http-Method");
            MDC.remove("User-Id");
        }
    }
}

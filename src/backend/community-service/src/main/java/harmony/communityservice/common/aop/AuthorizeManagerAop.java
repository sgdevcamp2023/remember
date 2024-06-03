package harmony.communityservice.common.aop;

import harmony.communityservice.common.dto.CommonCommand;
import harmony.communityservice.guild.guild.application.port.in.VerifyGuildManagerQuery;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class AuthorizeManagerAop {

    private final VerifyGuildManagerQuery verifyGuildManagerQuery;

    @Pointcut("@annotation(harmony.communityservice.common.annotation.AuthorizeGuildManager)")
    public void AuthorizeGuildManager() {
    }

    @Around("AuthorizeGuildManager()")
    public Object Authorize(ProceedingJoinPoint joinPoint) throws Throwable {
        Object firstArg = joinPoint.getArgs()[0];
        if (firstArg instanceof CommonCommand commonCommand) {
            verifyGuildManagerQuery.verify(commonCommand.getGuildId(), commonCommand.getUserId());
        }
        return joinPoint.proceed();
    }
}

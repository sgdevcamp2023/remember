package harmony.communityservice.common.aop;

import harmony.communityservice.common.dto.CommonCommand;
import harmony.communityservice.common.dto.CommonRequest;
import harmony.communityservice.guild.guild.application.port.in.VerifyGuildMemberCommand;
import harmony.communityservice.guild.guild.application.port.in.VerifyGuildUserQuery;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class AuthorizeGuildMemberAop {

    private final VerifyGuildUserQuery verifyGuildUserQuery;

    @Pointcut("@annotation(harmony.communityservice.common.annotation.AuthorizeGuildMember)")
    public void AuthorizeGuildMember() {
    }

    @Around("AuthorizeGuildMember()")
    public Object Authorize(ProceedingJoinPoint joinPoint) throws Throwable {
        Object firstArg = joinPoint.getArgs()[0];
        if (firstArg instanceof CommonCommand command) {
            verifyGuildUserQuery.verify(new VerifyGuildMemberCommand(command.getUserId(), command.getGuildId()));
        }
        return joinPoint.proceed();
    }
}

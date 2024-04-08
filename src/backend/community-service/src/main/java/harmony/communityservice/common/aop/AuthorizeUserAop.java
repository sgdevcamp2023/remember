package harmony.communityservice.common.aop;

import harmony.communityservice.common.dto.CommonRequest;
import harmony.communityservice.common.dto.VerifyGuildMemberRequest;
import harmony.communityservice.user.service.query.UserReadQueryService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class AuthorizeUserAop {

    private final UserReadQueryService userReadQueryService;

    @Pointcut("@annotation(harmony.communityservice.common.annotation.AuthorizeGuildMember)")
    public void AuthorizeGuildMember() {
    }

    @Around("AuthorizeGuildMember()")
    public Object Authorize(ProceedingJoinPoint joinPoint) throws Throwable {
        Object firstArg = joinPoint.getArgs()[0];
        if (firstArg instanceof CommonRequest request) {
            userReadQueryService.existsByUserIdAndGuildId(
                    new VerifyGuildMemberRequest(request.getUserId(), request.getGuildId()));
        }
        return joinPoint.proceed();
    }
}

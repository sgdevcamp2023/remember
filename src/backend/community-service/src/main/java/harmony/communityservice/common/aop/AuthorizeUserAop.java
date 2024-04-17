package harmony.communityservice.common.aop;

import harmony.communityservice.common.dto.CommonRequest;
import harmony.communityservice.common.dto.ManagerRequest;
import harmony.communityservice.common.dto.VerifyUserRequest;
import harmony.communityservice.common.exception.WrongUserException;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class AuthorizeUserAop {

    @Pointcut("@within(harmony.communityservice.common.annotation.AuthorizeUser)")
    public void AuthorizeUser() {
    }

    @Around("AuthorizeUser()")
    public Object Authorize(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Long passport = Long.valueOf(request.getHeader("user-id"));
        Object firstArg = joinPoint.getArgs()[0];
        if (firstArg instanceof CommonRequest commonRequest) {
            Long userId = commonRequest.getUserId();
            verifyUserId(passport, userId);
        } else if (firstArg instanceof ManagerRequest managerRequest) {
            Long managerId = managerRequest.getManagerId();
            verifyUserId(passport, managerId);
        } else if (firstArg instanceof VerifyUserRequest verifyUserRequest) {
            Long userId = verifyUserRequest.getUserId();
            verifyUserId(passport, userId);
        } else if (firstArg instanceof Long userId) {
            verifyUserId(passport, userId);
        } else {
            throw new WrongUserException("잘못된 접근입니다. 처음부터 다시 시도해주기길 바랍니다.");
        }
        return joinPoint.proceed();
    }

    private void verifyUserId(Long passport, Long userId) {
        if (!passport.equals(userId)) {
            throw new WrongUserException("잘못된 접근입니다. 처음부터 다시 시도해주시길 바랍니다");
        }
    }
}

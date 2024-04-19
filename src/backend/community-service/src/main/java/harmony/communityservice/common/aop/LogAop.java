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
public class LogAop {

    private final InfoLogPrinter infoLogPrinter;

    @Pointcut("execution(* harmony.communityservice.board.board..*.*(..))")
    public void board() {
    }

    @Pointcut("execution(* harmony.communityservice.board.comment..*.*(..))")
    public void comment() {
    }

    @Pointcut("execution(* harmony.communityservice.board.emoji..*.*(..))")
    public void emoji() {
    }

    @Pointcut("execution(* harmony.communityservice.common.service.impl.*.*(..))")
    public void common() {
    }

    @Pointcut("execution(* harmony.communityservice.guild.category..*.*(..))")
    public void category() {
    }

    @Pointcut("execution(* harmony.communityservice.guild.channel..*.*(..))")
    public void channel() {
    }

    @Pointcut("execution(* harmony.communityservice.guild.guild..*.*(..))")
    public void guild() {
    }

    @Pointcut("execution(* harmony.communityservice.room..*.*(..))")
    public void room() {
    }

    @Pointcut("execution(* harmony.communityservice.user..*.*(..))")
    public void user() {
    }




    @Around("board()||comment()||common()||category()||channel()||guild()||room()||user()")
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

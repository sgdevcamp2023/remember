package harmony.communityservice.common.aop;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import harmony.communityservice.common.utils.InfoLogPrinter;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class LogAopTest {

    @Mock
    private ProceedingJoinPoint joinPoint;

    @Mock
    private InfoLogPrinter infoLogPrinter;

    @InjectMocks
    private LogAop logAop;

    @Mock
    private Signature signature;

    @Test
    @DisplayName("로그 테스트")
    void log() throws Throwable {

        given(joinPoint.proceed()).willReturn(null);
        given(joinPoint.getSignature()).willReturn(signature);
        given(joinPoint.getTarget()).willReturn(new Object());
        given(joinPoint.getTarget().getClass().getSimpleName()).willReturn("test");
        given(signature.getName()).willReturn("methodName");

        logAop.board();
        logAop.comment();
        logAop.emoji();
        logAop.common();
        logAop.category();
        logAop.channel();
        logAop.guild();
        logAop.room();
        logAop.user();
        logAop.logging(joinPoint);

        then(infoLogPrinter).should().logging("String" + "-" + signature.getName());

    }
}
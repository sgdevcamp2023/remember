package harmony.communityservice.common.aop;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;

import harmony.communityservice.guild.guild.application.port.in.DeleteGuildCommand;
import harmony.communityservice.guild.guild.application.port.in.VerifyGuildManagerQuery;
import org.aspectj.lang.ProceedingJoinPoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AuthorizeManagerAopTest {

    AuthorizeManagerAop authorizeManagerAop;
    @Mock
    private ProceedingJoinPoint joinPoint;
    @Mock
    private VerifyGuildManagerQuery verifyGuildManagerQuery;

    @BeforeEach
    void setting() {
        authorizeManagerAop = new AuthorizeManagerAop(verifyGuildManagerQuery);
    }

    @Test
    @DisplayName("AuthorizeManagerAop test")
    void authorize_manager() throws Throwable {
        DeleteGuildCommand deleteGuildCommand = new DeleteGuildCommand(1L, 1L);
        given(joinPoint.getArgs()).willReturn(new Object[]{deleteGuildCommand});
        given(joinPoint.proceed()).willReturn(null);
        willDoNothing().given(verifyGuildManagerQuery).verify(1L, 1L);

        authorizeManagerAop.Authorize(joinPoint);

        then(joinPoint).should(times(1)).proceed();
        then(verifyGuildManagerQuery).should(times(1)).verify(1L, 1L);
    }
}
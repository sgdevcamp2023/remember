package harmony.communityservice.common.aop;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;

import harmony.communityservice.board.board.application.port.in.RegisterBoardCommand;
import harmony.communityservice.guild.category.application.port.in.DeleteCategoryCommand;
import harmony.communityservice.guild.category.application.port.in.LoadListCommand;
import harmony.communityservice.guild.category.application.port.in.ModifyCategoryCommand;
import harmony.communityservice.guild.category.application.port.in.RegisterCategoryCommand;
import harmony.communityservice.guild.channel.application.port.in.DeleteChannelCommand;
import harmony.communityservice.guild.channel.application.port.in.LoadChannelsCommand;
import harmony.communityservice.guild.channel.application.port.in.RegisterChannelCommand;
import harmony.communityservice.guild.guild.application.port.in.LoadGuildUserStatesCommand;
import harmony.communityservice.guild.guild.application.port.in.LoadInvitationCodeCommand;
import harmony.communityservice.guild.guild.application.port.in.VerifyGuildMemberCommand;
import harmony.communityservice.guild.guild.application.port.in.VerifyGuildUserQuery;
import org.aspectj.lang.ProceedingJoinPoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AuthorizeGuildMemberAopTest {

    AuthorizeGuildMemberAop authorizeGuildMemberAop;
    @Mock
    private ProceedingJoinPoint joinPoint;
    @Mock
    private VerifyGuildUserQuery verifyGuildUserQuery;

    @BeforeEach
    void setting() {
        authorizeGuildMemberAop = new AuthorizeGuildMemberAop(verifyGuildUserQuery);
    }

    @Test
    @DisplayName("AuthorizeGuildMemberAop loadInvitationCodeCommand Test")
    void authorize_loadInvitationCodeCommand_test() throws Throwable {
        LoadInvitationCodeCommand loadInvitationCodeCommand = new LoadInvitationCodeCommand(1L, 1L);
        given(joinPoint.getArgs()).willReturn(new Object[]{loadInvitationCodeCommand});
        willDoNothing().given(verifyGuildUserQuery).verify(new VerifyGuildMemberCommand(1L, 1L));
        given(joinPoint.proceed()).willReturn(null);

        authorizeGuildMemberAop.AuthorizeGuildMember();
        authorizeGuildMemberAop.Authorize(joinPoint);

        then(verifyGuildUserQuery).should(times(1)).verify(new VerifyGuildMemberCommand(1L, 1L));
        then(joinPoint).should(times(1)).proceed();
    }

    @Test
    @DisplayName("AuthorizeGuildMemberAop registerBoardCommand Test")
    void authorize_registerBoardCommand_test() throws Throwable {
        RegisterBoardCommand registerBoardCommand = new RegisterBoardCommand(1L, 1L, 1L, "title", "content");
        given(joinPoint.getArgs()).willReturn(new Object[]{registerBoardCommand});
        willDoNothing().given(verifyGuildUserQuery).verify(new VerifyGuildMemberCommand(1L, 1L));
        given(joinPoint.proceed()).willReturn(null);

        authorizeGuildMemberAop.Authorize(joinPoint);

        then(verifyGuildUserQuery).should(times(1)).verify(new VerifyGuildMemberCommand(1L, 1L));
        then(joinPoint).should(times(1)).proceed();
    }

    @Test
    @DisplayName("AuthorizeGuildMemberAop registerCategoryCommand Test")
    void authorize_registerCategoryCommand_test() throws Throwable {
        RegisterCategoryCommand registerCategoryCommand = new RegisterCategoryCommand("category", 1L, 1L);
        given(joinPoint.getArgs()).willReturn(new Object[]{registerCategoryCommand});
        willDoNothing().given(verifyGuildUserQuery).verify(new VerifyGuildMemberCommand(1L, 1L));
        given(joinPoint.proceed()).willReturn(null);

        authorizeGuildMemberAop.Authorize(joinPoint);

        then(verifyGuildUserQuery).should(times(1)).verify(new VerifyGuildMemberCommand(1L, 1L));
        then(joinPoint).should(times(1)).proceed();
    }

    @Test
    @DisplayName("AuthorizeGuildMemberAop deleteCategoryCommand Test")
    void authorize_deleteCategoryCommand_test() throws Throwable {
        DeleteCategoryCommand deleteCategoryCommand = new DeleteCategoryCommand(1L, 1L, 1L);
        given(joinPoint.getArgs()).willReturn(new Object[]{deleteCategoryCommand});
        willDoNothing().given(verifyGuildUserQuery).verify(new VerifyGuildMemberCommand(1L, 1L));
        given(joinPoint.proceed()).willReturn(null);

        authorizeGuildMemberAop.Authorize(joinPoint);

        then(verifyGuildUserQuery).should(times(1)).verify(new VerifyGuildMemberCommand(1L, 1L));
        then(joinPoint).should(times(1)).proceed();
    }

    @Test
    @DisplayName("AuthorizeGuildMemberAop modifyCategoryCommand Test")
    void authorize_modifyCategoryCommand_test() throws Throwable {
        ModifyCategoryCommand modifyCategoryCommand = new ModifyCategoryCommand(1L, 1L, 1L, "NEW_CATEGORY");
        given(joinPoint.getArgs()).willReturn(new Object[]{modifyCategoryCommand});
        willDoNothing().given(verifyGuildUserQuery).verify(new VerifyGuildMemberCommand(1L, 1L));
        given(joinPoint.proceed()).willReturn(null);

        authorizeGuildMemberAop.Authorize(joinPoint);

        then(verifyGuildUserQuery).should(times(1)).verify(new VerifyGuildMemberCommand(1L, 1L));
        then(joinPoint).should(times(1)).proceed();
    }

    @Test
    @DisplayName("AuthorizeGuildMemberAop loadListCommand Test")
    void authorize_loadListCommand_test() throws Throwable {
        LoadListCommand loadListCommand = new LoadListCommand(1L, 1L);
        given(joinPoint.getArgs()).willReturn(new Object[]{loadListCommand});
        willDoNothing().given(verifyGuildUserQuery).verify(new VerifyGuildMemberCommand(1L, 1L));
        given(joinPoint.proceed()).willReturn(null);

        authorizeGuildMemberAop.Authorize(joinPoint);

        then(verifyGuildUserQuery).should(times(1)).verify(new VerifyGuildMemberCommand(1L, 1L));
        then(joinPoint).should(times(1)).proceed();
    }

    @Test
    @DisplayName("AuthorizeGuildMemberAop registerChannelCommand Test")
    void authorize_registerChannelCommand_test() throws Throwable {
        RegisterChannelCommand registerChannelCommand = new RegisterChannelCommand(1L, "channel", 1L, 1L, "FORUM");
        given(joinPoint.getArgs()).willReturn(new Object[]{registerChannelCommand});
        willDoNothing().given(verifyGuildUserQuery).verify(new VerifyGuildMemberCommand(1L, 1L));
        given(joinPoint.proceed()).willReturn(null);

        authorizeGuildMemberAop.Authorize(joinPoint);

        then(verifyGuildUserQuery).should(times(1)).verify(new VerifyGuildMemberCommand(1L, 1L));
        then(joinPoint).should(times(1)).proceed();
    }

    @Test
    @DisplayName("AuthorizeGuildMemberAop loadChannelsCommand Test")
    void authorize_loadChannelsCommand_test() throws Throwable {
        LoadChannelsCommand loadChannelsCommand = new LoadChannelsCommand(1L, 1L);
        given(joinPoint.getArgs()).willReturn(new Object[]{loadChannelsCommand});
        willDoNothing().given(verifyGuildUserQuery).verify(new VerifyGuildMemberCommand(1L, 1L));
        given(joinPoint.proceed()).willReturn(null);

        authorizeGuildMemberAop.Authorize(joinPoint);

        then(verifyGuildUserQuery).should(times(1)).verify(new VerifyGuildMemberCommand(1L, 1L));
        then(joinPoint).should(times(1)).proceed();
    }

    @Test
    @DisplayName("AuthorizeGuildMemberAop loadGuildUserStatesCommand Test")
    void authorize_loadGuildUserStatesCommand_test() throws Throwable {
        LoadGuildUserStatesCommand loadGuildUserStatesCommand = new LoadGuildUserStatesCommand(1L, 1L);
        given(joinPoint.getArgs()).willReturn(new Object[]{loadGuildUserStatesCommand});
        willDoNothing().given(verifyGuildUserQuery).verify(new VerifyGuildMemberCommand(1L, 1L));
        given(joinPoint.proceed()).willReturn(null);

        authorizeGuildMemberAop.Authorize(joinPoint);

        then(verifyGuildUserQuery).should(times(1)).verify(new VerifyGuildMemberCommand(1L, 1L));
        then(joinPoint).should(times(1)).proceed();
    }

    @Test
    @DisplayName("AuthorizeGuildMemberAop deleteChannelCommand Test")
    void authorize_deleteChannelCommand_test() throws Throwable {
        DeleteChannelCommand deleteChannelCommand = new DeleteChannelCommand(1L,1L,1L,"FORUM");
        given(joinPoint.getArgs()).willReturn(new Object[]{deleteChannelCommand});
        willDoNothing().given(verifyGuildUserQuery).verify(new VerifyGuildMemberCommand(1L, 1L));
        given(joinPoint.proceed()).willReturn(null);

        authorizeGuildMemberAop.Authorize(joinPoint);

        then(verifyGuildUserQuery).should(times(1)).verify(new VerifyGuildMemberCommand(1L, 1L));
        then(joinPoint).should(times(1)).proceed();
    }

}
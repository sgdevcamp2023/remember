package harmony.communityservice.common.aop;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

import harmony.communityservice.board.board.adapter.in.web.DeleteBoardRequest;
import harmony.communityservice.board.board.adapter.in.web.ModifyBoardRequest;
import harmony.communityservice.board.board.adapter.in.web.RegisterBoardRequest;
import harmony.communityservice.board.comment.adapter.in.web.DeleteCommentRequest;
import harmony.communityservice.board.comment.adapter.in.web.ModifyCommentRequest;
import harmony.communityservice.board.comment.adapter.in.web.RegisterCommentRequest;
import harmony.communityservice.board.emoji.adapter.in.web.DeleteEmojiRequest;
import harmony.communityservice.board.emoji.adapter.in.web.RegisterEmojiRequest;
import harmony.communityservice.common.exception.WrongUserException;
import harmony.communityservice.guild.category.adapter.in.web.DeleteCategoryRequest;
import harmony.communityservice.guild.category.adapter.in.web.ModifyCategoryRequest;
import harmony.communityservice.guild.category.adapter.in.web.RegisterCategoryRequest;
import harmony.communityservice.guild.channel.adapter.in.web.DeleteChannelRequest;
import harmony.communityservice.guild.channel.adapter.in.web.RegisterChannelRequest;
import harmony.communityservice.guild.guild.adapter.in.web.DeleteGuildRequest;
import harmony.communityservice.guild.guild.adapter.in.web.LoadGuildInvitationCodeRequest;
import harmony.communityservice.guild.guild.adapter.in.web.ModifyUserNicknameInGuildRequest;
import harmony.communityservice.guild.guild.adapter.in.web.RegisterGuildRequest;
import harmony.communityservice.room.adapter.in.web.DeleteRoomRequest;
import harmony.communityservice.room.adapter.in.web.RegisterRoomRequest;
import harmony.communityservice.user.adapter.in.web.ModifyUserNicknameRequest;
import harmony.communityservice.user.adapter.in.web.ModifyUserProfileRequest;
import harmony.communityservice.user.adapter.in.web.RegisterUserRequest;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import org.aspectj.lang.ProceedingJoinPoint;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@ExtendWith(MockitoExtension.class)
class AuthorizeUserAopTest {

    @Mock
    private ProceedingJoinPoint joinPoint;

    @Mock
    HttpServletRequest request;

    @InjectMocks
    private AuthorizeUserAop authorizeUserAop;

    @Test
    @DisplayName("authorize registerUserRequest test")
    void authorize_registerUserRequest_test() throws Throwable {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("user-id", "1");
        ServletRequestAttributes attributes = new ServletRequestAttributes(request);
        RequestContextHolder.setRequestAttributes(attributes);
        RegisterUserRequest registerUserRequest = new RegisterUserRequest(1L, "seaweed.0chord@gmail.com", "0chord",
                "https://cdn.com/user");
        given(joinPoint.getArgs()).willReturn(new Object[]{registerUserRequest});
        given(joinPoint.proceed()).willReturn(null);

        authorizeUserAop.AuthorizeUser();
        authorizeUserAop.Authorize(joinPoint);

        then(joinPoint).should(times(1)).proceed();
    }

    @Test
    @DisplayName("authorizeUser exception test")
    void authorize_exception_test() throws Throwable {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("user-id", "2");
        ServletRequestAttributes attributes = new ServletRequestAttributes(request);
        RequestContextHolder.setRequestAttributes(attributes);
        RegisterUserRequest registerUserRequest = new RegisterUserRequest(1L, "seaweed.0chord@gmail.com", "0chord",
                "https://cdn.com/user");
        given(joinPoint.getArgs()).willReturn(new Object[]{registerUserRequest});

        assertThrows(WrongUserException.class, () -> authorizeUserAop.Authorize(joinPoint));
    }

    @Test
    @DisplayName("authorizeUser exception test2")
    void authorize_exception_test2() throws Throwable {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("user-id", "abc");
        ServletRequestAttributes attributes = new ServletRequestAttributes(request);
        RequestContextHolder.setRequestAttributes(attributes);

        assertThrows(NumberFormatException.class, () -> authorizeUserAop.Authorize(joinPoint));
    }

    @Test
    @DisplayName("authorize modifyUserProfileRequest test")
    void authorize_modifyUserProfileRequest_test() throws Throwable {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("user-id", "1");
        ServletRequestAttributes attributes = new ServletRequestAttributes(request);
        RequestContextHolder.setRequestAttributes(attributes);
        ModifyUserProfileRequest modifyUserProfileRequest = new ModifyUserProfileRequest(1L, "https://cdn.com/new");
        given(joinPoint.getArgs()).willReturn(new Object[]{modifyUserProfileRequest});
        given(joinPoint.proceed()).willReturn(null);

        authorizeUserAop.Authorize(joinPoint);

        then(joinPoint).should(times(1)).proceed();
    }

    @Test
    @DisplayName("authorize modifyUserNicknameRequest test")
    void authorize_modifyUserNicknameRequest_test() throws Throwable {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("user-id", "1");
        ServletRequestAttributes attributes = new ServletRequestAttributes(request);
        RequestContextHolder.setRequestAttributes(attributes);
        ModifyUserNicknameRequest modifyUserNicknameRequest = new ModifyUserNicknameRequest(1L, "NEW_NICKNAME");
        given(joinPoint.getArgs()).willReturn(new Object[]{modifyUserNicknameRequest});
        given(joinPoint.proceed()).willReturn(null);

        authorizeUserAop.Authorize(joinPoint);

        then(joinPoint).should(times(1)).proceed();
    }

    @Test
    @DisplayName("authorize registerRoomRequest test")
    void authorize_registerRoomRequest_test() throws Throwable {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("user-id", "1");
        ServletRequestAttributes attributes = new ServletRequestAttributes(request);
        RequestContextHolder.setRequestAttributes(attributes);
        RegisterRoomRequest registerRoomRequest = new RegisterRoomRequest("room", List.of(1L,2L),"https://cdn.com/profile",1L);
        given(joinPoint.getArgs()).willReturn(new Object[]{registerRoomRequest});
        given(joinPoint.proceed()).willReturn(null);

        authorizeUserAop.Authorize(joinPoint);

        then(joinPoint).should(times(1)).proceed();
    }

    @Test
    @DisplayName("authorize deleteRoomRequest test")
    void authorize_deleteRoomRequest_test() throws Throwable {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("user-id", "1");
        ServletRequestAttributes attributes = new ServletRequestAttributes(request);
        RequestContextHolder.setRequestAttributes(attributes);
        DeleteRoomRequest deleteRoomRequest = new DeleteRoomRequest(1L,1L,2L);
        given(joinPoint.getArgs()).willReturn(new Object[]{deleteRoomRequest});
        given(joinPoint.proceed()).willReturn(null);

        authorizeUserAop.Authorize(joinPoint);

        then(joinPoint).should(times(1)).proceed();
    }

    @Test
    @DisplayName("authorize userId test")
    void authorize_userId_test() throws Throwable {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("user-id", "1");
        ServletRequestAttributes attributes = new ServletRequestAttributes(request);
        RequestContextHolder.setRequestAttributes(attributes);
        given(joinPoint.getArgs()).willReturn(new Object[]{1L});
        given(joinPoint.proceed()).willReturn(null);

        authorizeUserAop.Authorize(joinPoint);

        then(joinPoint).should(times(1)).proceed();
    }

    @Test
    @DisplayName("authorize user states userId test")
    void authorize_user_states_userId_test() throws Throwable {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("user-id", "1");
        ServletRequestAttributes attributes = new ServletRequestAttributes(request);
        RequestContextHolder.setRequestAttributes(attributes);
        given(joinPoint.getArgs()).willReturn(new Object[]{1L,1L});
        given(joinPoint.proceed()).willReturn(null);

        authorizeUserAop.Authorize(joinPoint);

        then(joinPoint).should(times(1)).proceed();
    }

    @Test
    @DisplayName("authorize registerGuildRequest test")
    void authorize_registerGuildRequest_test() throws Throwable {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("user-id", "1");
        ServletRequestAttributes attributes = new ServletRequestAttributes(request);
        RequestContextHolder.setRequestAttributes(attributes);
        RegisterGuildRequest registerGuildRequest = new RegisterGuildRequest(1L, "guild_name");
        given(joinPoint.getArgs()).willReturn(new Object[]{registerGuildRequest});
        given(joinPoint.proceed()).willReturn(null);

        authorizeUserAop.Authorize(joinPoint);

        then(joinPoint).should(times(1)).proceed();
    }

    @Test
    @DisplayName("authorize modifyUserNicknameInGuildRequest test")
    void authorize_modifyUserNicknameInGuildRequest_test() throws Throwable {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("user-id", "1");
        ServletRequestAttributes attributes = new ServletRequestAttributes(request);
        RequestContextHolder.setRequestAttributes(attributes);
        ModifyUserNicknameInGuildRequest modifyUserNicknameInGuildRequest = new ModifyUserNicknameInGuildRequest(1L,1L,"NEW_NICKNAME");
        given(joinPoint.getArgs()).willReturn(new Object[]{modifyUserNicknameInGuildRequest});
        given(joinPoint.proceed()).willReturn(null);

        authorizeUserAop.Authorize(joinPoint);

        then(joinPoint).should(times(1)).proceed();
    }

    @Test
    @DisplayName("authorize guild list userId test")
    void authorize_guild_list_userId_test() throws Throwable {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("user-id", "1");
        ServletRequestAttributes attributes = new ServletRequestAttributes(request);
        RequestContextHolder.setRequestAttributes(attributes);
        given(joinPoint.getArgs()).willReturn(new Object[]{1L});
        given(joinPoint.proceed()).willReturn(null);

        authorizeUserAop.Authorize(joinPoint);

        then(joinPoint).should(times(1)).proceed();
    }

    @Test
    @DisplayName("authorize loadGuildInvitationCodeRequest test")
    void authorize_loadGuildInvitationCodeRequest_test() throws Throwable {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("user-id", "1");
        ServletRequestAttributes attributes = new ServletRequestAttributes(request);
        RequestContextHolder.setRequestAttributes(attributes);
        LoadGuildInvitationCodeRequest loadGuildInvitationCodeRequest = new LoadGuildInvitationCodeRequest(1L, 1L);
        given(joinPoint.getArgs()).willReturn(new Object[]{loadGuildInvitationCodeRequest});
        given(joinPoint.proceed()).willReturn(null);

        authorizeUserAop.Authorize(joinPoint);

        then(joinPoint).should(times(1)).proceed();
    }

    @Test
    @DisplayName("authorize user state user id test")
    void authorize_user_state_user_id_test() throws Throwable {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("user-id", "1");
        ServletRequestAttributes attributes = new ServletRequestAttributes(request);
        RequestContextHolder.setRequestAttributes(attributes);
        given(joinPoint.getArgs()).willReturn(new Object[]{1L,1L});
        given(joinPoint.proceed()).willReturn(null);

        authorizeUserAop.Authorize(joinPoint);

        then(joinPoint).should(times(1)).proceed();
    }

    @Test
    @DisplayName("authorize invitation code user id test")
    void authorize_invitation_join_user_id_test() throws Throwable {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("user-id", "1");
        ServletRequestAttributes attributes = new ServletRequestAttributes(request);
        RequestContextHolder.setRequestAttributes(attributes);
        given(joinPoint.getArgs()).willReturn(new Object[]{1L,"code"});
        given(joinPoint.proceed()).willReturn(null);

        authorizeUserAop.Authorize(joinPoint);

        then(joinPoint).should(times(1)).proceed();
    }

    @Test
    @DisplayName("authorize deleteGuildRequest test")
    void authorize_deleteGuildRequest_test() throws Throwable {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("user-id", "1");
        ServletRequestAttributes attributes = new ServletRequestAttributes(request);
        RequestContextHolder.setRequestAttributes(attributes);
        DeleteGuildRequest deleteGuildRequest = new DeleteGuildRequest(1L, 1L);
        given(joinPoint.getArgs()).willReturn(new Object[]{deleteGuildRequest});
        given(joinPoint.proceed()).willReturn(null);

        authorizeUserAop.Authorize(joinPoint);

        then(joinPoint).should(times(1)).proceed();
    }

    @Test
    @DisplayName("authorize registerChannelRequest test")
    void authorize_registerChannelRequest_test() throws Throwable {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("user-id", "1");
        ServletRequestAttributes attributes = new ServletRequestAttributes(request);
        RequestContextHolder.setRequestAttributes(attributes);
        RegisterChannelRequest registerChannelRequest = new RegisterChannelRequest(1L,"CHANNEL",1L,1L,"FORUM");
        given(joinPoint.getArgs()).willReturn(new Object[]{registerChannelRequest});
        given(joinPoint.proceed()).willReturn(null);

        authorizeUserAop.Authorize(joinPoint);

        then(joinPoint).should(times(1)).proceed();
    }

    @Test
    @DisplayName("authorize load channel user id test")
    void authorize_load_channel_user_id_test() throws Throwable {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("user-id", "1");
        ServletRequestAttributes attributes = new ServletRequestAttributes(request);
        RequestContextHolder.setRequestAttributes(attributes);
        given(joinPoint.getArgs()).willReturn(new Object[]{1L,1L});
        given(joinPoint.proceed()).willReturn(null);

        authorizeUserAop.Authorize(joinPoint);

        then(joinPoint).should(times(1)).proceed();
    }

    @Test
    @DisplayName("authorize deleteChannelRequest test")
    void authorize_deleteChannelRequest_test() throws Throwable {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("user-id", "1");
        ServletRequestAttributes attributes = new ServletRequestAttributes(request);
        RequestContextHolder.setRequestAttributes(attributes);
        DeleteChannelRequest deleteChannelRequest = new DeleteChannelRequest(1L, 1L, 1L, "FORUM");
        given(joinPoint.getArgs()).willReturn(new Object[]{deleteChannelRequest});
        given(joinPoint.proceed()).willReturn(null);

        authorizeUserAop.Authorize(joinPoint);

        then(joinPoint).should(times(1)).proceed();
    }

    @Test
    @DisplayName("authorize registerCategoryRequest test")
    void authorize_registerCategoryRequest_test() throws Throwable {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("user-id", "1");
        ServletRequestAttributes attributes = new ServletRequestAttributes(request);
        RequestContextHolder.setRequestAttributes(attributes);
        RegisterCategoryRequest registerCategoryRequest = new RegisterCategoryRequest("CATEGORY",1L,1L);
        given(joinPoint.getArgs()).willReturn(new Object[]{registerCategoryRequest});
        given(joinPoint.proceed()).willReturn(null);

        authorizeUserAop.Authorize(joinPoint);

        then(joinPoint).should(times(1)).proceed();
    }

    @Test
    @DisplayName("authorize modifyCategoryRequest test")
    void authorize_modifyCategoryRequest_test() throws Throwable {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("user-id", "1");
        ServletRequestAttributes attributes = new ServletRequestAttributes(request);
        RequestContextHolder.setRequestAttributes(attributes);
        ModifyCategoryRequest modifyCategoryRequest = new ModifyCategoryRequest(1L,1L,1L,"NEW_NAME");
        given(joinPoint.getArgs()).willReturn(new Object[]{modifyCategoryRequest});
        given(joinPoint.proceed()).willReturn(null);

        authorizeUserAop.Authorize(joinPoint);

        then(joinPoint).should(times(1)).proceed();
    }

    @Test
    @DisplayName("authorize load category user id test")
    void authorize_load_category_user_id_test() throws Throwable {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("user-id", "1");
        ServletRequestAttributes attributes = new ServletRequestAttributes(request);
        RequestContextHolder.setRequestAttributes(attributes);
        given(joinPoint.getArgs()).willReturn(new Object[]{1L,1L});
        given(joinPoint.proceed()).willReturn(null);

        authorizeUserAop.Authorize(joinPoint);

        then(joinPoint).should(times(1)).proceed();
    }

    @Test
    @DisplayName("authorize deleteCategoryRequest test")
    void authorize_deleteCategoryRequest_test() throws Throwable {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("user-id", "1");
        ServletRequestAttributes attributes = new ServletRequestAttributes(request);
        RequestContextHolder.setRequestAttributes(attributes);
        DeleteCategoryRequest deleteCategoryRequest = new DeleteCategoryRequest(1L, 1L, 1L);
        given(joinPoint.getArgs()).willReturn(new Object[]{deleteCategoryRequest});
        given(joinPoint.proceed()).willReturn(null);

        authorizeUserAop.Authorize(joinPoint);

        then(joinPoint).should(times(1)).proceed();
    }

    @Test
    @DisplayName("authorize registerEmojiRequest test")
    void authorize_registerEmojiRequest_test() throws Throwable {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("user-id", "1");
        ServletRequestAttributes attributes = new ServletRequestAttributes(request);
        RequestContextHolder.setRequestAttributes(attributes);
        RegisterEmojiRequest registerEmojiRequest = new RegisterEmojiRequest(1L,1L,1L);
        given(joinPoint.getArgs()).willReturn(new Object[]{registerEmojiRequest});
        given(joinPoint.proceed()).willReturn(null);

        authorizeUserAop.Authorize(joinPoint);

        then(joinPoint).should(times(1)).proceed();
    }

    @Test
    @DisplayName("authorize deleteEmojiRequest test")
    void authorize_deleteEmojiRequest_test() throws Throwable {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("user-id", "1");
        ServletRequestAttributes attributes = new ServletRequestAttributes(request);
        RequestContextHolder.setRequestAttributes(attributes);
        DeleteEmojiRequest deleteEmojiRequest = new DeleteEmojiRequest(1L,1L);
        given(joinPoint.getArgs()).willReturn(new Object[]{deleteEmojiRequest});
        given(joinPoint.proceed()).willReturn(null);

        authorizeUserAop.Authorize(joinPoint);

        then(joinPoint).should(times(1)).proceed();
    }

    @Test
    @DisplayName("authorize registerCommentRequest test")
    void authorize_registerCommentRequest_test() throws Throwable {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("user-id", "1");
        ServletRequestAttributes attributes = new ServletRequestAttributes(request);
        RequestContextHolder.setRequestAttributes(attributes);
        RegisterCommentRequest registerCommentRequest = new RegisterCommentRequest(1L,1L,"comment","0chord","https://cdn.com/user");
        given(joinPoint.getArgs()).willReturn(new Object[]{registerCommentRequest});
        given(joinPoint.proceed()).willReturn(null);

        authorizeUserAop.Authorize(joinPoint);

        then(joinPoint).should(times(1)).proceed();
    }

    @Test
    @DisplayName("authorize modifyCommentRequest test")
    void authorize_modifyCommentRequest_test() throws Throwable {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("user-id", "1");
        ServletRequestAttributes attributes = new ServletRequestAttributes(request);
        RequestContextHolder.setRequestAttributes(attributes);
        ModifyCommentRequest modifyCommentRequest = new ModifyCommentRequest(1L,1L,1L,"NEW_COMMENT");
        given(joinPoint.getArgs()).willReturn(new Object[]{modifyCommentRequest});
        given(joinPoint.proceed()).willReturn(null);

        authorizeUserAop.Authorize(joinPoint);

        then(joinPoint).should(times(1)).proceed();
    }

    @Test
    @DisplayName("authorize deleteCommentRequest test")
    void authorize_deleteCommentRequest_test() throws Throwable {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("user-id", "1");
        ServletRequestAttributes attributes = new ServletRequestAttributes(request);
        RequestContextHolder.setRequestAttributes(attributes);
        DeleteCommentRequest deleteCommentRequest = new DeleteCommentRequest(1L,1L,1L);
        given(joinPoint.getArgs()).willReturn(new Object[]{deleteCommentRequest});
        given(joinPoint.proceed()).willReturn(null);

        authorizeUserAop.Authorize(joinPoint);

        then(joinPoint).should(times(1)).proceed();
    }

    @Test
    @DisplayName("authorize registerBoardRequest test")
    void authorize_registerBoardRequest_test() throws Throwable {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("user-id", "1");
        ServletRequestAttributes attributes = new ServletRequestAttributes(request);
        RequestContextHolder.setRequestAttributes(attributes);
        RegisterBoardRequest registerBoardRequest = new RegisterBoardRequest(1L,1L,1L,"title","content");
        given(joinPoint.getArgs()).willReturn(new Object[]{registerBoardRequest});
        given(joinPoint.proceed()).willReturn(null);

        authorizeUserAop.Authorize(joinPoint);

        then(joinPoint).should(times(1)).proceed();
    }

    @Test
    @DisplayName("authorize modifyBoardRequest test")
    void authorize_modifyBoardRequest_test() throws Throwable {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("user-id", "1");
        ServletRequestAttributes attributes = new ServletRequestAttributes(request);
        RequestContextHolder.setRequestAttributes(attributes);
        ModifyBoardRequest modifyBoardRequest = new ModifyBoardRequest(1L,1L,"NEW_TITLE","NEW_CONTENT");
        given(joinPoint.getArgs()).willReturn(new Object[]{modifyBoardRequest});
        given(joinPoint.proceed()).willReturn(null);

        authorizeUserAop.Authorize(joinPoint);

        then(joinPoint).should(times(1)).proceed();
    }

    @Test
    @DisplayName("authorize load boards user id test")
    void authorize_load_boards_user_id_test() throws Throwable {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("user-id", "1");
        ServletRequestAttributes attributes = new ServletRequestAttributes(request);
        RequestContextHolder.setRequestAttributes(attributes);
        given(joinPoint.getArgs()).willReturn(new Object[]{1L,1L,1L,50});
        given(joinPoint.proceed()).willReturn(null);

        authorizeUserAop.Authorize(joinPoint);

        then(joinPoint).should(times(1)).proceed();
    }

    @Test
    @DisplayName("authorize load board user id test")
    void authorize_load_board_user_id_test() throws Throwable {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("user-id", "1");
        ServletRequestAttributes attributes = new ServletRequestAttributes(request);
        RequestContextHolder.setRequestAttributes(attributes);
        given(joinPoint.getArgs()).willReturn(new Object[]{1L,1L});
        given(joinPoint.proceed()).willReturn(null);

        authorizeUserAop.Authorize(joinPoint);

        then(joinPoint).should(times(1)).proceed();
    }

    @Test
    @DisplayName("authorize deleteBoardRequest test")
    void authorize_deleteBoardRequest_test() throws Throwable {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("user-id", "1");
        ServletRequestAttributes attributes = new ServletRequestAttributes(request);
        RequestContextHolder.setRequestAttributes(attributes);
        DeleteBoardRequest deleteBoardRequest = new DeleteBoardRequest(1L, 1L);
        given(joinPoint.getArgs()).willReturn(new Object[]{deleteBoardRequest});
        given(joinPoint.proceed()).willReturn(null);

        authorizeUserAop.Authorize(joinPoint);

        then(joinPoint).should(times(1)).proceed();
    }

    @Test
    @DisplayName("authorize user exception test")
    void authorize_user_exception_test() throws Throwable {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("user-id", "1");
        ServletRequestAttributes attributes = new ServletRequestAttributes(request);
        RequestContextHolder.setRequestAttributes(attributes);;
        given(joinPoint.getArgs()).willReturn(new Object[]{"abc"});

        Assertions.assertThrows(WrongUserException.class, ()->authorizeUserAop.Authorize(joinPoint));

    }
}
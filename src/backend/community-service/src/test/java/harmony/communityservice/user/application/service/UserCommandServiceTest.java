package harmony.communityservice.user.application.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;

import harmony.communityservice.user.application.port.in.ModifyUserNicknameCommand;
import harmony.communityservice.user.application.port.in.ModifyUserProfileCommand;
import harmony.communityservice.user.application.port.in.RegisterUserCommand;
import harmony.communityservice.user.application.port.out.LoadUserCommandPort;
import harmony.communityservice.user.application.port.out.ModifyUserInfoPort;
import harmony.communityservice.user.application.port.out.RegisterUserPort;
import harmony.communityservice.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserCommandServiceTest {

    @Mock
    RegisterUserPort registerUserPort;

    @Mock
    LoadUserCommandPort loadUserCommandPort;

    @Mock
    ModifyUserInfoPort modifyUserInfoPort;

    UserCommandService userCommandService;

    User user;

    @BeforeEach
    void setting() {
        userCommandService = new UserCommandService(registerUserPort, loadUserCommandPort,
                modifyUserInfoPort);

        user = User.builder()
                .userId(1L)
                .nickname("0chord")
                .email("seaweed.0chord@gmail.com")
                .profile("https://storage.googleapis.com/sg-dev-remember-harmony/discord.png")
                .build();
    }

    @Test
    @DisplayName("user 등록 테스트")
    void register_user() {
        assertNotNull(userCommandService);

        willDoNothing().given(registerUserPort).register(user);

        RegisterUserCommand registerUserCommand = new RegisterUserCommand(1L, "seaweed.0chord@gmail.com",
                "0chord", "https://storage.googleapis.com/sg-dev-remember-harmony/discord.png");

        userCommandService.register(registerUserCommand);

        then(registerUserPort).should(times(1)).register(user);
    }


    @Test
    @DisplayName("유저 프로필 정보 수정 테스트")
    void modify_user_profile() {
        assertNotNull(userCommandService);

        given(loadUserCommandPort.loadUser(1L)).willReturn(user);

        ModifyUserProfileCommand modifyUserProfileCommand = new ModifyUserProfileCommand(1L,
                "https://storage.googleapis.com/sg-dev-remember-harmony/test.png");

        userCommandService.modifyProfile(modifyUserProfileCommand);

        then(modifyUserInfoPort).should(times(1)).modifyUserInfo(user);
    }

    @Test
    @DisplayName("유저 닉네임 정보 수정 테스트")
    void modify_user_nickname() {
        assertNotNull(userCommandService);

        given(loadUserCommandPort.loadUser(1L)).willReturn(user);

        ModifyUserNicknameCommand modifyUserNicknameCommand = new ModifyUserNicknameCommand(1L,
                "0Chord");

        userCommandService.modifyNickname(modifyUserNicknameCommand);

        then(modifyUserInfoPort).should(times(1)).modifyUserInfo(user);
    }

}
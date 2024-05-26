package harmony.communityservice.user.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;

import harmony.communityservice.user.application.port.out.LoadUserQueryPort;
import harmony.communityservice.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserQueryServiceTest {

    @Mock
    LoadUserQueryPort loadUserQueryPort;

    UserQueryService userQueryService;

    @BeforeEach
    void set() {
        userQueryService = new UserQueryService(loadUserQueryPort);
    }


    @Test
    @DisplayName("유저 조회 테스트")
    void load_user() {
        assertNotNull(userQueryService);

        User user = User.builder()
                .userId(1L)
                .nickname("0chord")
                .email("seaweed.0chord@gmail.com")
                .profile("https://storage.googleapis.com/sg-dev-remember-harmony/discord.png")
                .build();

        given(loadUserQueryPort.loadUser(1L)).willReturn(user);

        userQueryService.loadUser(1L);

        assertEquals(1L, user.getUserId().getId());
    }
}
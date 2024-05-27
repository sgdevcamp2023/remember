package harmony.communityservice.user.adapter.out.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;

import harmony.communityservice.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@EnableTransactionManagement
@Transactional(readOnly = true)
class UserQueryPersistenceAdapterTest {

    @Autowired
    UserQueryPersistenceAdapter userQueryPersistenceAdapter;

    @Autowired
    UserCommandPersistenceAdapter userCommandPersistenceAdapter;

    @Autowired
    UserQueryRepository userQueryRepository;

    @BeforeEach
    @Transactional(readOnly = false)
    void setting() {
        User user = User.builder()
                .userId(1L)
                .email("seaweed.0chord@gmail.com")
                .profile("https://storage.googleapis.com/sg-dev-remember-harmony/discord.png")
                .nickname("0chord")
                .build();
        userCommandPersistenceAdapter.register(user);
    }

    @Test
    @DisplayName("유저 조회 테스트")
    void load_user() {
        User loadUser = userQueryPersistenceAdapter.loadUser(1L);
        assertEquals(1L, loadUser.getUserId().getId());
    }
}
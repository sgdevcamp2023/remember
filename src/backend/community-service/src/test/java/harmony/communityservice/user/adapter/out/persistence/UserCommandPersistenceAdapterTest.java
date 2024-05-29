package harmony.communityservice.user.adapter.out.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import harmony.communityservice.common.exception.NotFoundDataException;
import harmony.communityservice.user.domain.User;
import harmony.communityservice.user.domain.User.UserId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
@EnableTransactionManagement
class UserCommandPersistenceAdapterTest {

    @Autowired
    UserCommandPersistenceAdapter userCommandPersistenceAdapter;

    @Autowired
    UserCommandRepository userCommandRepository;

    @Test
    @DisplayName("유저 등록 JPA 테스트")
    void register_user() {
        User user = User.builder()
                .userId(1L)
                .email("seaweed.0chord@gmail.com")
                .profile("https://storage.googleapis.com/sg-dev-remember-harmony/discord.png")
                .nickname("0chord")
                .build();
        userCommandPersistenceAdapter.register(user);

        UserEntity userEntity = userCommandRepository.findById(UserIdJpaVO.make(1L))
                .orElseThrow(NotFoundDataException::new);

        assertEquals(user.getUserId().getId(), userEntity.getUserId().getId());
    }

    @Test
    @DisplayName("유저 조회 JPA 테스트")
    void load_user() {
        User user = User.builder()
                .userId(1L)
                .email("seaweed.0chord@gmail.com")
                .profile("https://storage.googleapis.com/sg-dev-remember-harmony/discord.png")
                .nickname("0chord")
                .build();
        userCommandPersistenceAdapter.register(user);

        User loadUser = userCommandPersistenceAdapter.loadUser(1L);
        UserEntity userEntity = userCommandRepository.findById(UserIdJpaVO.make(1L))
                .orElseThrow(NotFoundDataException::new);

        assertEquals(user, loadUser);
        assertEquals(loadUser.getUserId().getId(), userEntity.getUserId().getId());
    }

    @Test
    @DisplayName("존재하지 않는 유저 조회 JPA 테스트")
    void load_not_exists_user() {
        User user = User.builder()
                .userId(1L)
                .email("seaweed.0chord@gmail.com")
                .profile("https://storage.googleapis.com/sg-dev-remember-harmony/discord.png")
                .nickname("0chord")
                .build();
        userCommandPersistenceAdapter.register(user);

        assertThrows(NotFoundDataException.class, () -> userCommandPersistenceAdapter.loadUser(2L));
    }

    @Test
    @DisplayName("유저 프로필 수정 JPA 테스트")
    void modify_user_profile() {
        User user = User.builder()
                .userId(1L)
                .email("seaweed.0chord@gmail.com")
                .profile("https://storage.googleapis.com/sg-dev-remember-harmony/discord.png")
                .nickname("0chord")
                .build();
        userCommandPersistenceAdapter.register(user);
        String newProfileUrl = "https://cdn.com/user";
        userCommandPersistenceAdapter.modifyProfile(UserId.make(1L), newProfileUrl);

        User loadUser = userCommandPersistenceAdapter.loadUser(1L);
        UserEntity userEntity = userCommandRepository.findById(UserIdJpaVO.make(1L))
                .orElseThrow(NotFoundDataException::new);

        assertEquals(loadUser.getUserInfo().getCommonUserInfo().getProfile(), newProfileUrl);
        assertEquals(loadUser.getUserInfo().getCommonUserInfo().getProfile(),
                userEntity.getUserInfo().getCommonUserInfo().getUserProfile());
    }

    @Test
    @DisplayName("유저 닉네임 수정 JPA 테스트")
    void modify_user_nickname() {
        User user = User.builder()
                .userId(1L)
                .email("seaweed.0chord@gmail.com")
                .profile("https://storage.googleapis.com/sg-dev-remember-harmony/discord.png")
                .nickname("0chord")
                .build();
        userCommandPersistenceAdapter.register(user);
        String newNickname = "0Chord";
        userCommandPersistenceAdapter.modifyNickname(UserId.make(1L), newNickname);

        User loadUser = userCommandPersistenceAdapter.loadUser(1L);
        UserEntity userEntity = userCommandRepository.findById(UserIdJpaVO.make(1L))
                .orElseThrow(NotFoundDataException::new);


        assertEquals(loadUser.getUserInfo().getCommonUserInfo().getNickname(), newNickname);
        assertEquals(loadUser.getUserInfo().getCommonUserInfo().getNickname(),
                userEntity.getUserInfo().getCommonUserInfo().getNickname());
    }
}
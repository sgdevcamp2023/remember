package harmony.communityservice.user.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import harmony.communityservice.common.exception.WrongThresholdRangeException;
import harmony.communityservice.user.domain.User.UserId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class UserTest {

    @Test
    @DisplayName("식별자가 같을 때 같은 유저로 인식 테스트")
    void same_user1() {

        User firstUser = User.builder()
                .userId(1L)
                .email("test1@test.com")
                .profile("https://cdn.com/test1")
                .nickname("test1")
                .build();

        User secondUser = User.builder()
                .userId(1L)
                .email("test2@test.com")
                .profile("https://cdn.com/test2")
                .nickname("test2")
                .build();

        boolean equals = firstUser.equals(secondUser);

        assertThat(equals).isTrue();
    }

    @Test
    @DisplayName("식별자가 다를 때 같은 값을 가져도 다른 유저로 인식 테스트")
    void same_user2() {

        User firstUser = User.builder()
                .userId(1L)
                .email("test@test.com")
                .profile("https://cdn.com/test")
                .nickname("test")
                .build();

        User secondUser = User.builder()
                .userId(2L)
                .email("test@test.com")
                .profile("https://cdn.com/test")
                .nickname("test")
                .build();

        boolean equals = firstUser.equals(secondUser);

        assertThat(equals).isFalse();
    }

    @Test
    @DisplayName("객체가 null일 때 다른 객체로 인식하는지 테스트")
    void equals_object_null() {

        User user = User.builder()
                .userId(1L)
                .email("test@test.com")
                .profile("https://cdn.com/test")
                .nickname("test")
                .build();

        boolean equals = user.equals(null);

        assertThat(equals).isFalse();
    }

    @Test
    @DisplayName("다른 객체일 때 다른 객체로 인식하는지 테스트")
    void equals_object() {

        User user = User.builder()
                .userId(1L)
                .email("test@test.com")
                .profile("https://cdn.com/test")
                .nickname("test")
                .build();

        CommonUserInfo commonUserInfo = CommonUserInfo.make("test", "https://cdn.com/test");

        boolean equals = user.equals(commonUserInfo);

        assertThat(equals).isFalse();
    }

    @Test
    @DisplayName("객체가 null일 때 인식하는지 테스트")
    void null_object() {

        User user = User.builder()
                .userId(1L)
                .email("test@test.com")
                .profile("https://cdn.com/test")
                .nickname("test")
                .build();


        boolean equals = user.equals(null);

        assertThat(equals).isFalse();
    }

    @Test
    @DisplayName("식별자의 값이 같을 때 다른 객체라도 같은 식별자로 인식하는지 테스트")
    void same_user_id() {
        UserId firstUserId = UserId.make(1L);
        UserId secondUserId = UserId.make(1L);

        assertSame(true, firstUserId.equals(secondUserId));
    }

    @ParameterizedTest
    @DisplayName("UserId 범위 테스트")
    @ValueSource(longs = {0L, -1L, -10L, -100L, -1000L})
    void modified_user(long userId) {
        assertThrows(WrongThresholdRangeException.class, () -> User.builder()
                .userId(userId)
                .email("test@test.com")
                .profile("https://cdn.com/test")
                .nickname("test")
                .build());
    }

}
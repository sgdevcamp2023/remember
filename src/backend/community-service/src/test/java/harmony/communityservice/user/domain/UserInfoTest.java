package harmony.communityservice.user.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserInfoTest {

    @Test
    @DisplayName("value가 모두 일치할 때 같은 객체로 인식 테스트")
    void same_userinfo() {
        UserInfo firstUserInfo = UserInfo.make("test@test.com", "https://cdn.com/test", "test");
        UserInfo secondUserInfo = UserInfo.make("test@test.com", "https://cdn.com/test", "test");

        boolean equals = firstUserInfo.equals(secondUserInfo);

        assertSame(equals, true);
    }

    @Test
    @DisplayName("value가 다를 때 다른 객체로 인식 테스트")
    void different_userinfo() {
        UserInfo firstUserInfo = UserInfo.make("test1@test1.com", "https://cdn.com/test1", "test1");
        UserInfo secondUserInfo = UserInfo.make("test2@test2.com", "https://cdn.com/test2", "test2");

        boolean equals = firstUserInfo.equals(secondUserInfo);

        assertSame(equals, false);
    }

    @Test
    @DisplayName("수정 시 다른 객체로 인식 테스트")
    void modify_userinfo() {
        UserInfo userInfo = UserInfo.make("test1@test1.com", "https://cdn.com/test1", "test1");
        UserInfo modifiedUserInfo = userInfo.modifyProfile("https://cdn.com/test2");

        boolean equals = userInfo.equals(modifiedUserInfo);

        assertSame(equals, false);
    }
}
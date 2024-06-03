package harmony.communityservice.user.domain;

import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CommonUserInfoTest {


    @Test
    @DisplayName("value가 모두 일치할 때 같은 객체로 인식 테스트")
    void same_commonUserInfo() {
        CommonUserInfo firstCommonUserInfo = CommonUserInfo.make("test", "https://cdn.com/test");
        CommonUserInfo secondCommonUserInfo = CommonUserInfo.make("test", "https://cdn.com/test");
        boolean equals = firstCommonUserInfo.equals(secondCommonUserInfo);

        assertSame(true, equals);
    }

    @Test
    @DisplayName("value가 다를 때 다른 객체로 인식 테스트")
    void different_commonUserInfo() {
        CommonUserInfo firstCommonUserInfo = CommonUserInfo.make("test1", "https://cdn.com/test1");
        CommonUserInfo secondCommonUserInfo = CommonUserInfo.make("test2", "https://cdn.com/test2");

        boolean equals = firstCommonUserInfo.equals(secondCommonUserInfo);

        assertSame(false, equals);
    }

    @Test
    @DisplayName("수정 시 다른 객체로 인식 테스트")
    void modify_userinfo() {
        CommonUserInfo commonUserInfo = CommonUserInfo.make("test", "https://cdn.com/test");
        CommonUserInfo modifiedCommonUserInfo = commonUserInfo.modifyProfile("https://cdn.com/test2");

        boolean equals = commonUserInfo.equals(modifiedCommonUserInfo);

        assertSame(false, equals);
    }
}
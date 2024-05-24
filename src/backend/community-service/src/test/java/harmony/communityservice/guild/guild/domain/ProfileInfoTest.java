package harmony.communityservice.guild.guild.domain;

import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProfileInfoTest {

    @Test
    @DisplayName("value가 모두 일치할 때 같은 객체로 인식 테스트")
    void same_profileInfo() {
        ProfileInfo firstProfileInfo = ProfileInfo.make("test", "http://cdn.com/test");
        ProfileInfo secondProfileInfo = ProfileInfo.make("test", "http://cdn.com/test");

        boolean equals = firstProfileInfo.equals(secondProfileInfo);

        assertSame(equals, true);
    }

    @Test
    @DisplayName("value가 모두 다를 때 다른 객체로 인식 테스트")
    void different_profileInfo() {
        ProfileInfo firstProfileInfo = ProfileInfo.make("test1", "http://cdn.com/test1");
        ProfileInfo secondProfileInfo = ProfileInfo.make("test2", "http://cdn.com/test2");

        boolean equals = firstProfileInfo.equals(secondProfileInfo);

        assertSame(equals, false);
    }

}
package harmony.communityservice.board.comment.domain;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CommonUserInfo {
    private final String nickname;
    private final String profile;

    static CommonUserInfo make(String nickname, String profile) {
        return new CommonUserInfo(nickname, profile);
    }

    CommonUserInfo modifyNickname(String nickname) {
        return new CommonUserInfo(nickname, this.profile);
    }

    CommonUserInfo modifyProfile(String profile) {
        return new CommonUserInfo(this.nickname, profile);
    }
}

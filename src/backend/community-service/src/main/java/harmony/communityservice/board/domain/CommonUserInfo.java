package harmony.communityservice.board.domain;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CommonUserInfo {

    @NotBlank
    private String nickname;

    @NotBlank
    private String profile;

    public static CommonUserInfo make(String nickname, String profile) {
        return new CommonUserInfo(nickname, profile);
    }

    public CommonUserInfo modifyProfile(String profile) {
        return new CommonUserInfo(this.nickname, profile);
    }

    public CommonUserInfo modifyNickname(String nickname) {
        return new CommonUserInfo(nickname, this.profile);
    }
}

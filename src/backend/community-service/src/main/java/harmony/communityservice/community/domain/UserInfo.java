package harmony.communityservice.community.domain;

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
public class UserInfo {

    @NotBlank
    private String email;

    @NotBlank
    private String nickname;

    @NotBlank
    private String profile;

    public static UserInfo make(String email, String nickname, String profile) {
        return new UserInfo(email, nickname, profile);
    }

    public UserInfo modifyProfile(String profile) {
        return new UserInfo(this.email, this.nickname, profile);
    }

    public UserInfo modifyNickname(String nickname) {
        return new UserInfo(this.email, nickname, this.profile);
    }
}

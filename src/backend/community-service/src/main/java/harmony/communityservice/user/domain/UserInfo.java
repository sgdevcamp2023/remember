package harmony.communityservice.user.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserInfo {

    @NotBlank
    private String email;

    @Embedded
    private CommonUserInfo commonUserInfo;

    public static UserInfo make(String email, String nickname, String profile) {
        CommonUserInfo commonUserInfoVO = CommonUserInfo.make(nickname, profile);
        return new UserInfo(email, commonUserInfoVO);
    }

    public UserInfo modifyProfile(String profile) {
        CommonUserInfo newCommonUserInfo = commonUserInfo.modifyProfile(profile);
        return new UserInfo(this.email, newCommonUserInfo);
    }

    public UserInfo modifyNickname(String nickname) {
        CommonUserInfo newCommonUserInfo = commonUserInfo.modifyNickname(nickname);
        return new UserInfo(this.email, newCommonUserInfo);
    }
}

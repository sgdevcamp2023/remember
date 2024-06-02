package harmony.communityservice.user.domain;


import harmony.communityservice.domain.ValueObject;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserInfo extends ValueObject<UserInfo> {
    private final String email;
    private final CommonUserInfo commonUserInfo;

    private UserInfo(String profile, String nickname, String email) {
        this.commonUserInfo = CommonUserInfo.make(nickname, profile);
        this.email = email;
    }

    static UserInfo make(String email, String profile, String nickname) {
        return new UserInfo(profile, nickname, email);
    }

    UserInfo modifyProfile(String profile) {
        CommonUserInfo modifiedCommonUserInfo = commonUserInfo.modifyProfile(profile);
        return new UserInfo(this.email, modifiedCommonUserInfo);
    }
}

package harmony.communityservice.user.domain;


import harmony.communityservice.domain.ValueObject;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CommonUserInfo extends ValueObject<CommonUserInfo> {
    private final String nickname;
    private final String profile;

    static CommonUserInfo make(String nickname, String profile) {
        return new CommonUserInfo(nickname, profile);
    }

    CommonUserInfo modifyProfile(String profile) {
        return new CommonUserInfo(this.nickname, profile);
    }

    @Override
    protected Object[] getEqualityFields() {
        return new Object[]{nickname, profile};
    }
}

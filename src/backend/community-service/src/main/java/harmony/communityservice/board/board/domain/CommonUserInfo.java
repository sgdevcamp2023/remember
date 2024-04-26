package harmony.communityservice.board.board.domain;

import harmony.communityservice.common.domain.ValueObject;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommonUserInfo extends ValueObject<CommonUserInfo> {

    @NotBlank
    @Column(name = "nickname")
    private String nickname;

    @NotBlank
    @Column(name = "profile")
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

    @Override
    protected Object[] getEqualityFields() {
        return new Object[]{nickname, profile};
    }
}

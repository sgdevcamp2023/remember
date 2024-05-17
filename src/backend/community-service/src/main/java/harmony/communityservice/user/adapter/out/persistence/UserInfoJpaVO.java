package harmony.communityservice.user.adapter.out.persistence;

import harmony.communityservice.common.domain.ValueObject;
import harmony.communityservice.generic.CommonUserInfoJpaVO;
import jakarta.persistence.Column;
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
class UserInfoJpaVO extends ValueObject<UserInfoJpaVO> {

    @NotBlank
    @Column(name = "email")
    private String email;

    @Embedded
    private CommonUserInfoJpaVO commonUserInfo;

    public static UserInfoJpaVO make(String email, String nickname, String profile) {
        CommonUserInfoJpaVO commonUserInfoVO = CommonUserInfoJpaVO.make(nickname, profile);
        return new UserInfoJpaVO(email, commonUserInfoVO);
    }

    @Override
    protected Object[] getEqualityFields() {
        return new Object[]{email, commonUserInfo};
    }
}

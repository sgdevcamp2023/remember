package harmony.communityservice.common.generic;

import harmony.communityservice.common.domain.ValueObject;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WriterInfoJpaVO extends ValueObject<WriterInfoJpaVO> {

    @NotNull
    @Column(name = "writer_id")
    private Long writerId;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "nickname", column = @Column(name = "writer_name")),
            @AttributeOverride(name = "userProfile", column = @Column(name = "writer_profile"))
    })
    private CommonUserInfoJpaVO commonUserInfo;

    public static WriterInfoJpaVO make(String name, Long id, String profile) {
        CommonUserInfoJpaVO newCommonUserInfo = CommonUserInfoJpaVO.make(name, profile);
        return new WriterInfoJpaVO(id, newCommonUserInfo);
    }

    public void verifyWriter(Long writerId) {
        if (!this.writerId.equals(writerId)) {
            throw new IllegalStateException("Wrong Writer");
        }
    }

    @Override
    protected Object[] getEqualityFields() {
        return new Object[]{writerId, commonUserInfo};
    }
}

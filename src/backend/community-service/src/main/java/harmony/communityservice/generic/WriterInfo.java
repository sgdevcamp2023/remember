package harmony.communityservice.generic;

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
public class WriterInfo extends ValueObject<WriterInfo> {

    @NotNull
    @Column(name = "writer_id")
    private Long writerId;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "nickname", column = @Column(name = "writer_name")),
            @AttributeOverride(name = "profile", column = @Column(name = "writer_profile"))
    })
    private CommonUserInfoJpaVO commonUserInfo;

    public static WriterInfo make(String name, Long id, String profile) {
        CommonUserInfoJpaVO newCommonUserInfo = CommonUserInfoJpaVO.make(name, profile);
        return new WriterInfo(id, newCommonUserInfo);
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

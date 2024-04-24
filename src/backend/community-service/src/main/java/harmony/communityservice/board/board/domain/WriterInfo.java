package harmony.communityservice.board.board.domain;

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
public class WriterInfo {

    @NotNull
    @Column(name = "writer_id")
    private Long writerId;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "nickname", column = @Column(name = "writer_name")),
            @AttributeOverride(name = "profile", column = @Column(name = "writer_profile"))
    })
    private CommonUserInfo commonUserInfo;

    public static WriterInfo make(String name, Long id, String profile) {
        CommonUserInfo newCommonUserInfo = CommonUserInfo.make(name, profile);
        return new WriterInfo(id, newCommonUserInfo);
    }

    public void verifyWriter(Long writerId) {
        if (!this.writerId.equals(writerId)) {
            throw new IllegalStateException("Wrong Writer");
        }
    }

}

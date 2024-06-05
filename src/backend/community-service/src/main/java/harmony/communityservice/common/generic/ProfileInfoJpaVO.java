package harmony.communityservice.common.generic;

import harmony.communityservice.common.domainentity.EntityValueObject;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProfileInfoJpaVO extends EntityValueObject<ProfileInfoJpaVO> {

    @NotBlank
    @Column(name = "name")
    private String name;

    @NotBlank
    @Column(name = "profile")
    private String profile;


    private ProfileInfoJpaVO(String name, String profile) {
        this.name = name;
        this.profile = profile != null ? profile : "https://test.cdn.com/test";
    }

    public static ProfileInfoJpaVO make(String name, String profile) {
        return new ProfileInfoJpaVO(name, profile);
    }
}

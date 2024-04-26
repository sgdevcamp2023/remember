package harmony.communityservice.room.domain;

import harmony.communityservice.common.domain.ValueObject;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProfileInfo extends ValueObject<ProfileInfo> {

    @NotBlank
    @Column(name = "name")
    private String name;

    @NotBlank
    @Column(name = "profile")
    private String profile;


    private ProfileInfo(String name, String profile) {
        this.name = name;
        this.profile = profile != null ? profile : "https://test.cdn.com/test";
    }

    public static ProfileInfo make(String name, String profile) {
        return new ProfileInfo(name, profile);
    }

    @Override
    protected Object[] getEqualityFields() {
        return new Object[]{name, profile};
    }
}

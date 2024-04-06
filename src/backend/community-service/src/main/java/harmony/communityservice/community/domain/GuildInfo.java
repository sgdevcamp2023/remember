package harmony.communityservice.community.domain;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GuildInfo {

    @NotBlank
    private String name;

    @NotBlank
    private String profile;


    private GuildInfo(String name, String profile) {
        this.name = name;
        this.profile = profile != null ? profile : "https://test.cdn.com/test";
    }

    public static GuildInfo make(String name, String profile) {
        return new GuildInfo(name, profile);
    }
}

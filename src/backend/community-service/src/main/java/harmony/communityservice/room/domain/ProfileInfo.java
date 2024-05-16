package harmony.communityservice.room.domain;

import lombok.Getter;

@Getter
public class ProfileInfo {

    private String name;

    private String profile;

    private ProfileInfo(String name, String profile) {
        this.name = name;
        this.profile = profile != null ? profile : "https://test.cdn.com/test";
    }

    public static ProfileInfo make(String name, String profile) {
        return new ProfileInfo(name, profile);
    }
}

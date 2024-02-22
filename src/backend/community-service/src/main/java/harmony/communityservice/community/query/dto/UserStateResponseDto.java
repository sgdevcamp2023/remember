package harmony.communityservice.community.query.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserStateResponseDto {
    private Long userId;
    private String userName;
    private String profile;
    private String state;


    @Builder
    public UserStateResponseDto(Long userId, String userName, String profile, String state) {
        this.userId = userId;
        this.userName = userName;
        this.profile = profile;
        this.state = state;
    }

    public void updateState(String state) {
        this.state = state;
    }
}

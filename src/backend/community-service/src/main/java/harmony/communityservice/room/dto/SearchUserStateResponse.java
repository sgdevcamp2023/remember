package harmony.communityservice.room.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SearchUserStateResponse {
    private Long userId;
    private String userName;
    private String profile;
    private String state;


    @Builder
    public SearchUserStateResponse(Long userId, String userName, String profile, String state) {
        this.userId = userId;
        this.userName = userName;
        this.profile = profile;
        this.state = state;
    }

    public void modifyState(String state) {
        this.state = state;
    }
}

package harmony.communityservice.room.application.port.in;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
public class LoadUserStateResponse {
    private Long userId;
    private String userName;
    private String profile;
    private String state;


    @Builder
    public LoadUserStateResponse(Long userId, String userName, String profile, String state) {
        this.userId = userId;
        this.userName = userName;
        this.profile = profile;
        this.state = state;
    }

    public void modifyState(String state) {
        this.state = state;
    }
}

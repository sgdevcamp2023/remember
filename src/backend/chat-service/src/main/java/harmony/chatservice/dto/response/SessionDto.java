package harmony.chatservice.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SessionDto {

    private Long userId;
    private String sessionId;
    private String type;
    private String state;

    @Builder
    public SessionDto(Long userId, String sessionId, String type, String state) {
        this.userId = userId;
        this.sessionId = sessionId;
        this.type = type;
        this.state = state;
    }
}
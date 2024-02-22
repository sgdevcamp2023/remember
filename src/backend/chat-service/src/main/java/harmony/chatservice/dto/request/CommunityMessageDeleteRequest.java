package harmony.chatservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommunityMessageDeleteRequest {

    private Long guildId;
    private Long messageId;
    private String type;
}
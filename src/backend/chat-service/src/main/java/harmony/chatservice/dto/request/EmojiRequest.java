package harmony.chatservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmojiRequest {

    private Long guildId;
    private Long channelId;
    private Long roomId;
    private Long communityMessageId;
    private Long directMessageId;
    private Long userId;
    private Long typeId;
    private String type;
}
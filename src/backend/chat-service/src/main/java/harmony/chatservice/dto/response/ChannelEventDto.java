package harmony.chatservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChannelEventDto {

    private Long guildId;
    private Long channelId;
    private Long userId;
    private String type;
}
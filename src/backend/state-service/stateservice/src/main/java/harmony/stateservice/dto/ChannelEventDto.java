package harmony.stateservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChannelEventDto {

    private Long userId;
    private Long guildId;
    private Long channelId;
    private String type;
}
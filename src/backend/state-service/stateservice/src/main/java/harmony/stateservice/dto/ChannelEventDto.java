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

    private String userId;
    private String guildId;
    private String channelId;
    private String type;
}
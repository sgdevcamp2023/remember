package harmony.chatservice.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommunityEventDto {

    private String eventType;
    private Long guildId;
    private String channelName;
    private String channelType;
    private Long categoryId;
    private Long channelReadId;

    @Builder
    public CommunityEventDto(String eventType, Long guildId, String channelName, String channelType, Long categoryId,
                             Long channelReadId) {
        this.eventType = eventType;
        this.guildId = guildId;
        this.channelName = channelName;
        this.channelType = channelType;
        this.categoryId = categoryId;
        this.channelReadId = channelReadId;
    }
}

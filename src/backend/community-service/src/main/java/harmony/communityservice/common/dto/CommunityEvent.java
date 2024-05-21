package harmony.communityservice.common.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommunityEvent {
    private String type;
    private Long guildId;
    private Long guildReadId;
    private Long userId;
    private String name;
    private String profile;
    private String channelName;
    private String channelType;
    private Long categoryId;
    private Long channelId;


    @Builder
    public CommunityEvent(String type, Long guildId, Long guildReadId, Long userId, String name, String profile,
                          String channelName, String channelType, Long categoryId, Long channelId) {
        this.type = type;
        this.guildId = guildId;
        this.guildReadId = guildReadId;
        this.userId = userId;
        this.name = name;
        this.profile = profile;
        this.channelName = channelName;
        this.channelType = channelType;
        this.categoryId = categoryId;
        this.channelId = channelId;
    }
}

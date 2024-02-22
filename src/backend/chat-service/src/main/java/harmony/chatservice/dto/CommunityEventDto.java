package harmony.chatservice.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CommunityEventDto {

    private String type;
    private Long guildId;
    private Long guildReadId;
    private Long userId;
    private String name;
    private String profile;
    private String channelName;
    private String channelType;
    private Long categoryId;
    private Long channelReadId;

    @Builder
    public CommunityEventDto(String type, Long guildId, Long guildReadId, Long userId, String name, String profile, String channelName, String channelType,
                             Long categoryId,
                             Long channelReadId) {

        this.type = type;
        this.guildId = guildId;
        this.guildReadId = guildReadId;
        this.userId = userId;
        this.name = name;
        this.profile = profile;
        this.channelName = channelName;
        this.channelType = channelType;
        this.categoryId = categoryId;
        this.channelReadId = channelReadId;
    }
}

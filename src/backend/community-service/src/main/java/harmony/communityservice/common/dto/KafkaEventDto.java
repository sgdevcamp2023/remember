package harmony.communityservice.common.dto;

import lombok.Getter;

@Getter
public class KafkaEventDto {
    private String eventType;
    private Long guildId;
    private String channelName;
    private String channelType;
    private Long categoryId;
    private Long channelReadId;
}

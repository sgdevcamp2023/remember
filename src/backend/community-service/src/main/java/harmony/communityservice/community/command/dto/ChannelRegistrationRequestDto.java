package harmony.communityservice.community.command.dto;

import lombok.Builder.Default;
import lombok.Data;
import lombok.Getter;

@Data
public class ChannelRegistrationRequestDto {

    private Long guildId;
    private String name;
    private Long userId;
    private long categoryId = 0L;
    private String type;
}

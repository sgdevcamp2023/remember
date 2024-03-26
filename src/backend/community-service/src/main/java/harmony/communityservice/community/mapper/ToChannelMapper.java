package harmony.communityservice.community.mapper;

import harmony.communityservice.community.command.dto.RegisterChannelRequest;
import harmony.communityservice.community.domain.Channel;
import harmony.communityservice.community.domain.Guild;

public class ToChannelMapper {

    public static Channel convert(RegisterChannelRequest requestDto, Guild guild) {
        return Channel.builder()
                .name(requestDto.getName())
                .type(requestDto.getType())
                .categoryId(requestDto.getCategoryId())
                .guild(guild)
                .build();
    }
}

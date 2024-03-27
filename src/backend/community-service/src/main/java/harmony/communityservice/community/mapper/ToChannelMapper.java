package harmony.communityservice.community.mapper;

import harmony.communityservice.community.command.dto.RegisterChannelRequest;
import harmony.communityservice.community.domain.Channel;
import harmony.communityservice.community.domain.Guild;

public class ToChannelMapper {

    public static Channel convert(RegisterChannelRequest registerChannelRequest, Guild guild) {
        return Channel.builder()
                .name(registerChannelRequest.name())
                .type(registerChannelRequest.type())
                .categoryId(registerChannelRequest.categoryId())
                .guild(guild)
                .build();
    }
}

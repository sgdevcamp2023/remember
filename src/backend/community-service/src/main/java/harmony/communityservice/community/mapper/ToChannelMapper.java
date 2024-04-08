package harmony.communityservice.community.mapper;

import harmony.communityservice.community.command.dto.RegisterChannelRequest;
import harmony.communityservice.community.domain.Channel;

public class ToChannelMapper {

    public static Channel convert(RegisterChannelRequest registerChannelRequest) {
        return Channel.builder()
                .name(registerChannelRequest.name())
                .type(registerChannelRequest.type())
                .categoryId(registerChannelRequest.categoryId())
                .guildId(registerChannelRequest.guildId())
                .build();
    }
}

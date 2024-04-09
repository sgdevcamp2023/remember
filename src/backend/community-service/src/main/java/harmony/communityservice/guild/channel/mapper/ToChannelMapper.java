package harmony.communityservice.guild.channel.mapper;

import harmony.communityservice.guild.channel.dto.RegisterChannelRequest;
import harmony.communityservice.guild.domain.Channel;

public class ToChannelMapper {

    public static Channel convert(RegisterChannelRequest registerChannelRequest) {
        return Channel.builder()
                .name(registerChannelRequest.name())
                .type(registerChannelRequest.type())
                .categoryId(registerChannelRequest.categoryId())
                .build();
    }
}

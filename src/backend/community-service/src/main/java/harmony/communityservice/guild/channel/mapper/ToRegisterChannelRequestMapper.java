package harmony.communityservice.guild.channel.mapper;

import harmony.communityservice.common.event.dto.inner.RegisterChannelEvent;
import harmony.communityservice.guild.channel.dto.RegisterChannelRequest;

public class ToRegisterChannelRequestMapper {

    public static RegisterChannelRequest convert(RegisterChannelEvent event) {
        return new RegisterChannelRequest(event.guildId(), event.channelName(), event.userId(), event.categoryId(),
                event.type());
    }
}

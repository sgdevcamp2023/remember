package harmony.communityservice.guild.channel.mapper;

import harmony.communityservice.common.outbox.InnerEventRecord;
import harmony.communityservice.guild.channel.dto.RegisterChannelRequest;

public class ToRegisterChannelRequestMapper {

    public static RegisterChannelRequest convert(InnerEventRecord event) {
        return new RegisterChannelRequest(event.getGuildId(), event.getChannelName(), event.getUserId(),
                event.getCategoryId(),
                event.getChannelType());
    }
}

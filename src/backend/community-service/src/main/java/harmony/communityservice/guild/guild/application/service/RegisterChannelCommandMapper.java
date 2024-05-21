package harmony.communityservice.guild.guild.application.service;

import harmony.communityservice.common.outbox.InnerEventRecord;
import harmony.communityservice.guild.channel.application.port.in.RegisterChannelCommand;

public class RegisterChannelCommandMapper {

    public static RegisterChannelCommand convert(InnerEventRecord event) {
        return new RegisterChannelCommand(event.getGuildId(), event.getChannelName(), event.getUserId(),
                event.getCategoryId(),
                event.getChannelType());
    }
}

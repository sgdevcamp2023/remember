package harmony.communityservice.guild.guild.application.service;

import harmony.communityservice.common.outbox.InnerEventRecord;
import harmony.communityservice.guild.guild.application.port.in.RegisterGuildReadCommand;

public class RegisterGuildReadCommandMapper {

    public static RegisterGuildReadCommand convert(InnerEventRecord event) {
        return RegisterGuildReadCommand.builder()
                .userId(event.getUserId())
                .guildId(event.getGuildId())
                .profile(event.getGuildProfile())
                .name(event.getGuildName())
                .build();
    }
}

package harmony.communityservice.guild.guild.mapper;

import harmony.communityservice.common.outbox.InnerEventRecord;
import harmony.communityservice.guild.guild.dto.RegisterGuildReadRequest;

public class ToRegisterGuildReadRequestMapper {

    public static RegisterGuildReadRequest convert(InnerEventRecord event) {
        return RegisterGuildReadRequest.builder()
                .userId(event.getUserId())
                .guildId(event.getGuildId())
                .profile(event.getGuildProfile())
                .name(event.getGuildName())
                .build();
    }
}

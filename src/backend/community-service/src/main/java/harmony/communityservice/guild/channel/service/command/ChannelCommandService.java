package harmony.communityservice.guild.channel.service.command;

import harmony.communityservice.guild.channel.dto.DeleteChannelRequest;
import harmony.communityservice.guild.channel.dto.RegisterChannelRequest;

public interface ChannelCommandService {

    Long register(RegisterChannelRequest registerChannelRequest);

    void delete(DeleteChannelRequest deleteChannelRequest);

    void deleteByGuildId(Long guildId);
}

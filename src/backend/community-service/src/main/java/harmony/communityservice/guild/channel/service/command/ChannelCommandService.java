package harmony.communityservice.guild.channel.service.command;

import harmony.communityservice.guild.channel.dto.DeleteChannelRequest;
import harmony.communityservice.guild.channel.dto.RegisterChannelRequest;

public interface ChannelCommandService {

    Long registerChannel(RegisterChannelRequest registerChannelRequest);

    void delete(DeleteChannelRequest deleteChannelRequest);
}

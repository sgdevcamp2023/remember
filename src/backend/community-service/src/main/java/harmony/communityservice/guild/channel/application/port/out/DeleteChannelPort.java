package harmony.communityservice.guild.channel.application.port.out;

import harmony.communityservice.guild.channel.domain.Channel.ChannelId;

public interface DeleteChannelPort {
    void deleteById(ChannelId channelId);
}

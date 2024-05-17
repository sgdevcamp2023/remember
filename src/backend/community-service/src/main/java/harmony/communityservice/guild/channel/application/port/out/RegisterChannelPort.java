package harmony.communityservice.guild.channel.application.port.out;

import harmony.communityservice.guild.channel.domain.Channel;
import harmony.communityservice.guild.channel.domain.Channel.ChannelId;

public interface RegisterChannelPort {

    ChannelId register(Channel channel);
}

package harmony.communityservice.guild.channel.application.port.in;

import java.util.Map;

public interface LoadChannelsQuery {

    Map<Long, LoadChannelResponse> loadChannels(LoadChannelsCommand loadChannelsCommand);
}

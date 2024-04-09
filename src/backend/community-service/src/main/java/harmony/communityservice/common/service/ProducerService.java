package harmony.communityservice.common.service;

import harmony.communityservice.guild.channel.dto.RegisterChannelRequest;
import harmony.communityservice.guild.domain.GuildRead;

public interface ProducerService {
    void publishGuildDeletionEvent(Long guildId);

    void publishChannelCreationEvent(RegisterChannelRequest registerChannelRequest, Integer channelId);

    void publishChannelDeletionEvent(Integer channelId);

    void publishGuildCreationEvent(GuildRead guildRead);
}

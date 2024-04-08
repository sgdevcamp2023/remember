package harmony.communityservice.common.service;

import harmony.communityservice.guild.channel.dto.RegisterChannelRequest;
import harmony.communityservice.guild.domain.GuildRead;

public interface ProducerService {
    void publishGuildDeletionEvent(Long guildId);

    void publishChannelCreationEvent(RegisterChannelRequest registerChannelRequest, Long channelId);

    void publishChannelDeletionEvent(Long channelId);

    void publishGuildCreationEvent(GuildRead guildRead);
}

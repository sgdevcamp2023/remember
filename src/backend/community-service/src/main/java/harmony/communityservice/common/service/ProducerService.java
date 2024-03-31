package harmony.communityservice.common.service;

import harmony.communityservice.community.command.dto.RegisterChannelRequest;
import harmony.communityservice.community.domain.GuildRead;

public interface ProducerService {
    void publishGuildDeletionEvent(Long guildId);

    void publishChannelCreationEvent(RegisterChannelRequest registerChannelRequest, Long channelId);

    void publishChannelDeletionEvent(Long channelId);

    void publishGuildCreationEvent(GuildRead guildRead);
}

package harmony.communityservice.common.service;

import harmony.communityservice.community.domain.GuildRead;

public interface ProducerService {
    void publishGuildDeletionEvent(Long guildId);

    void publishChannelCreationEvent(Long guildId, Long categoryId, Long channelId, String channelName, String channelType);

    void publishChannelDeletionEvent(Long channelId);

    void publishGuildCreationEvent(GuildRead guildRead);
}

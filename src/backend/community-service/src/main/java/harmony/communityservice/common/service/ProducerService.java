package harmony.communityservice.common.service;

import harmony.communityservice.community.domain.GuildRead;

public interface ProducerService {
    void sendDeleteGuild(Long guildId);

    void sendCreateChannel(Long guildId, Long categoryId, Long channelId, String channelName, String channelType);

    void sendDeleteChannel(Long channelId);

    void sendCreateGuild(GuildRead guildRead);
}

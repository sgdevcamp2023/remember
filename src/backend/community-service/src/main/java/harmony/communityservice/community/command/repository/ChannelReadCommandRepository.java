package harmony.communityservice.community.command.repository;

import harmony.communityservice.community.domain.ChannelRead;

public interface ChannelReadCommandRepository {
    void save(ChannelRead channelRead);

    void deleteByChannelId(Long channelId);
}

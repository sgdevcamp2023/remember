package harmony.communityservice.community.command.repository;

import harmony.communityservice.community.domain.Channel;

public interface ChannelCommandRepository {
    void save(Channel channel);
}

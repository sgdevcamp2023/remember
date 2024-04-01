package harmony.communityservice.community.command.service;

import harmony.communityservice.community.domain.Channel;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ChannelReadCommandService {

    void register(long guildId, Channel channel);

    void delete(long channelId);
}

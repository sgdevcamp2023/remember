package harmony.communityservice.community.command.service;

import harmony.communityservice.community.domain.Channel;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ChannelReadCommandService {

    void registration(long guildId, Channel channel);
}

package harmony.communityservice.community.command.service;

import harmony.communityservice.community.domain.Guild;
import harmony.communityservice.community.domain.User;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface GuildUserCommandService {

    void save(Guild guild, User user);
}

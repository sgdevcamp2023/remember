package harmony.communityservice.community.command.service;

import harmony.communityservice.community.command.dto.RegisterGuildReadRequest;
import harmony.communityservice.community.domain.GuildRead;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface GuildReadCommandService {
    GuildRead register(RegisterGuildReadRequest registerGuildReadRequest);

    void delete(long guildId);
}

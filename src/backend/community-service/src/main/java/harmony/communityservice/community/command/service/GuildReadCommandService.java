package harmony.communityservice.community.command.service;

import harmony.communityservice.community.command.dto.GuildReadRequestDto;
import harmony.communityservice.community.domain.GuildRead;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface GuildReadCommandService {
    GuildRead save(GuildReadRequestDto requestDto);

    void delete(long guildId);
}

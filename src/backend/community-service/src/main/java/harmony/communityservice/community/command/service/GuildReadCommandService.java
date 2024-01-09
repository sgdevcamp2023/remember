package harmony.communityservice.community.command.service;

import harmony.communityservice.community.command.dto.GuildReadRequestDto;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface GuildReadCommandService {
    void save(GuildReadRequestDto requestDto);
}

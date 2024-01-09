package harmony.communityservice.community.command.service;

import harmony.communityservice.community.command.dto.GuildRegistrationRequestDto;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface GuildCommandService {

    void save(GuildRegistrationRequestDto requestDto, String profile);
}

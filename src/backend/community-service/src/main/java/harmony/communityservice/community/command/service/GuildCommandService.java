package harmony.communityservice.community.command.service;

import harmony.communityservice.community.command.dto.GuildDeleteRequestDto;
import harmony.communityservice.community.command.dto.GuildRegistrationRequestDto;
import harmony.communityservice.community.command.dto.GuildUpdateNicknameRequestDto;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface GuildCommandService {

    void save(GuildRegistrationRequestDto requestDto, String profile);

    void join(String invitationCode);

    void remove(GuildDeleteRequestDto guildDeleteRequestDto);

    void updateGuildNickname(GuildUpdateNicknameRequestDto requestDto);
}

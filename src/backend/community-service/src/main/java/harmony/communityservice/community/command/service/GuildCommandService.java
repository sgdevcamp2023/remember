package harmony.communityservice.community.command.service;

import harmony.communityservice.community.command.dto.GuildDeleteRequestDto;
import harmony.communityservice.community.command.dto.GuildRegistrationRequestDto;
import harmony.communityservice.community.command.dto.GuildUpdateNicknameRequestDto;
import harmony.communityservice.community.domain.GuildRead;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Transactional
public interface GuildCommandService {

    GuildRead save(GuildRegistrationRequestDto requestDto, MultipartFile profile);

    void join(String invitationCode, Long userId);

    void remove(GuildDeleteRequestDto guildDeleteRequestDto);

    void updateGuildNickname(GuildUpdateNicknameRequestDto requestDto);
}

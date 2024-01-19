package harmony.communityservice.community.command.service;

import harmony.communityservice.community.command.dto.GuildDeleteRequestDto;
import harmony.communityservice.community.command.dto.GuildRegistrationRequestDto;
import harmony.communityservice.community.command.dto.GuildUpdateNicknameRequestDto;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Transactional
public interface GuildCommandService {

    void save(GuildRegistrationRequestDto requestDto, MultipartFile profile);

    void join(String invitationCode, Long userId);

    void remove(GuildDeleteRequestDto guildDeleteRequestDto);

    void updateGuildNickname(GuildUpdateNicknameRequestDto requestDto);
}

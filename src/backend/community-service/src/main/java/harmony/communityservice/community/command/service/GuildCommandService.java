package harmony.communityservice.community.command.service;

import harmony.communityservice.community.command.dto.DeleteGuildRequest;
import harmony.communityservice.community.command.dto.RegisterGuildRequest;
import harmony.communityservice.community.command.dto.ModifyUserNicknameInGuildRequest;
import harmony.communityservice.community.domain.GuildRead;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Transactional
public interface GuildCommandService {

    GuildRead register(RegisterGuildRequest registerGuildRequest, MultipartFile profile);

    void joinByInvitationCode(String invitationCode, Long userId);

    void delete(DeleteGuildRequest deleteGuildRequest);

    void modifyUserNicknameInGuild(ModifyUserNicknameInGuildRequest modifyUserNicknameInGuildRequest);
}

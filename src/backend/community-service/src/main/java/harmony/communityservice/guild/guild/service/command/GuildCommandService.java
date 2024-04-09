package harmony.communityservice.guild.guild.service.command;

import harmony.communityservice.guild.guild.dto.DeleteGuildRequest;
import harmony.communityservice.guild.guild.dto.ModifyUserNicknameInGuildRequest;
import harmony.communityservice.guild.guild.dto.RegisterGuildRequest;
import harmony.communityservice.guild.guild.dto.RegisterUserUsingInvitationCodeRequest;
import harmony.communityservice.guild.domain.GuildRead;
import org.springframework.web.multipart.MultipartFile;

public interface GuildCommandService {

    GuildRead register(RegisterGuildRequest registerGuildRequest, MultipartFile profile);

    void joinByInvitationCode(RegisterUserUsingInvitationCodeRequest registerUserUsingInvitationCodeRequest);

    void delete(DeleteGuildRequest deleteGuildRequest);

    void modifyUserNicknameInGuild(ModifyUserNicknameInGuildRequest modifyUserNicknameInGuildRequest);
}

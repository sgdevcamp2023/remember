package harmony.communityservice.user.service.command;

import harmony.communityservice.guild.guild.dto.ModifyUserNicknameInGuildRequest;
import harmony.communityservice.user.domain.UserRead;
import harmony.communityservice.user.dto.RegisterUserReadRequest;

public interface UserReadCommandService {

    void register(RegisterUserReadRequest registerUserReadRequest);

    void modifyUserNicknameInGuild(ModifyUserNicknameInGuildRequest modifyUserNicknameInGuildRequest);

    UserRead searchByUserIdAndGuildId(Long userId, Long guildId);


}

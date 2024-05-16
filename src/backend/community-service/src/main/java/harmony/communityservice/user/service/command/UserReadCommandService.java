package harmony.communityservice.user.service.command;

import harmony.communityservice.guild.guild.dto.ModifyUserNicknameInGuildRequest;
import harmony.communityservice.user.adapter.out.persistence.UserReadEntity;
import harmony.communityservice.user.adapter.in.web.RegisterUserReadRequest;

public interface UserReadCommandService {

    void register(RegisterUserReadRequest registerUserReadRequest);

    void modifyUserNicknameInGuild(ModifyUserNicknameInGuildRequest modifyUserNicknameInGuildRequest);

    UserReadEntity searchByUserIdAndGuildId(Long userId, Long guildId);


}

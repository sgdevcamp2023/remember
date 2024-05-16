package harmony.communityservice.guild.guild.service.command;

import harmony.communityservice.guild.guild.dto.ModifyUserNicknameInGuildRequest;
import harmony.communityservice.guild.guild.dto.RegisterGuildReadRequest;
import harmony.communityservice.guild.guild.domain.GuildRead;
import harmony.communityservice.user.adapter.in.web.RegisterUserReadRequest;

public interface GuildReadCommandService {
    void register(RegisterGuildReadRequest registerGuildReadRequest);

    void delete(long guildId);

    void register(RegisterUserReadRequest registerUserReadRequest);

    void modifyUserNicknameInGuild(ModifyUserNicknameInGuildRequest modifyUserNicknameInGuildRequest);

    GuildRead searchByUserIdAndGuildId(Long userId, Long guildId);
}

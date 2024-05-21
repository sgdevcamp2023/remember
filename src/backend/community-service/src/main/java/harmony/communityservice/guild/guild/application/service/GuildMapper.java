package harmony.communityservice.guild.guild.application.service;

import harmony.communityservice.guild.guild.application.port.in.RegisterGuildCommand;
import harmony.communityservice.guild.guild.domain.Guild;
import harmony.communityservice.user.domain.User.UserId;
import java.util.UUID;

class GuildMapper {

    static Guild convert(RegisterGuildCommand guildCommand, String profileUrl) {
        return Guild.builder()
                .name(guildCommand.guildName())
                .profile(profileUrl)
                .managerId(UserId.make(guildCommand.managerId()))
                .inviteCode(UUID.randomUUID().toString().replace("-", ""))
                .build();
    }
}

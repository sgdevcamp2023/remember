package harmony.communityservice.guild.guild.application.service;

import harmony.communityservice.guild.guild.domain.Guild.GuildId;
import harmony.communityservice.guild.guild.domain.GuildRead;
import harmony.communityservice.guild.guild.application.port.in.RegisterGuildReadCommand;
import harmony.communityservice.user.domain.User;
import harmony.communityservice.user.domain.User.UserId;

class GuildReadMapper {

    static GuildRead convert(RegisterGuildReadCommand registerGuildReadCommand, User user) {
        return GuildRead.builder()
                .guildId(GuildId.make(registerGuildReadCommand.guildId()))
                .userId(UserId.make(registerGuildReadCommand.userId()))
                .profile(registerGuildReadCommand.profile())
                .name(registerGuildReadCommand.name())
                .userNickname(user.getUserInfo().getCommonUserInfo().getNickname())
                .userProfile(user.getUserInfo().getCommonUserInfo().getProfile())
                .build();
    }
}
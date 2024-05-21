package harmony.communityservice.guild.guild.adapter.out.persistence;

import harmony.communityservice.guild.guild.domain.Guild.GuildId;
import harmony.communityservice.guild.guild.domain.GuildRead;
import harmony.communityservice.guild.guild.domain.GuildRead.GuildReadId;
import harmony.communityservice.user.domain.User.UserId;

class GuildReadMapper {

    static GuildRead convert(GuildReadEntity guildReadEntity) {
        return GuildRead.builder()
                .userProfile(guildReadEntity.getCommonUserInfo().getUserProfile())
                .userNickname(guildReadEntity.getCommonUserInfo().getNickname())
                .guildId(GuildId.make(guildReadEntity.getGuildId().getId()))
                .guildReadId(GuildReadId.make(guildReadEntity.getGuildReadId().getId()))
                .profile(guildReadEntity.getGuildInfo().getProfile())
                .name(guildReadEntity.getGuildInfo().getName())
                .userId(UserId.make(guildReadEntity.getUserId().getId()))
                .build();
    }
}

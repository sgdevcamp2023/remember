package harmony.communityservice.guild.guild.adapter.out.persistence;

import harmony.communityservice.guild.guild.domain.Guild.GuildId;
import harmony.communityservice.guild.guild.domain.GuildRead;
import harmony.communityservice.guild.guild.domain.GuildRead.GuildReadId;

class GuildReadMapper {

    static GuildRead convert(GuildReadEntity guildReadEntity) {
        return GuildRead.builder()
                .userProfile(guildReadEntity.getCommonUserInfo().getProfile())
                .userNickname(guildReadEntity.getCommonUserInfo().getNickname())
                .guildId(GuildId.make(guildReadEntity.getGuildId().getId()))
                .guildReadId(GuildReadId.make(guildReadEntity.getGuildReadId().getId()))
                .profile(guildReadEntity.getGuildInfo().getProfile())
                .name(guildReadEntity.getGuildInfo().getName())
                .build();
    }
}

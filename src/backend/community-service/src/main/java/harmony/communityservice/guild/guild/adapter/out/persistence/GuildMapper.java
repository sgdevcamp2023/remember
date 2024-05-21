package harmony.communityservice.guild.guild.adapter.out.persistence;

import harmony.communityservice.guild.guild.domain.Guild;
import harmony.communityservice.guild.guild.domain.Guild.GuildId;
import harmony.communityservice.user.domain.User.UserId;

class GuildMapper {

    static Guild convert(GuildEntity guildEntity) {
        return Guild.builder()
                .guildId(GuildId.make(guildEntity.getGuildId().getId()))
                .name(guildEntity.getGuildInfo().getName())
                .managerId(UserId.make(guildEntity.getManagerId().getId()))
                .profile(guildEntity.getGuildInfo().getProfile())
                .inviteCode(guildEntity.getInviteCode())
                .build();
    }
}

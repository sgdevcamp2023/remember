package harmony.communityservice.guild.guild.adapter.out.persistence;

import harmony.communityservice.guild.guild.domain.Guild;
import harmony.communityservice.user.adapter.out.persistence.UserIdJpaVO;
import java.util.List;

class GuildEntityMapper {

    static GuildEntity convert(Guild guild) {
        List<GuildUserEntity> guildUserEntities = guild.getGuildUsers()
                .stream()
                .map(guildUser -> GuildUserEntity.make(UserIdJpaVO.make(guildUser.getUserId().getId())))
                .toList();

        return GuildEntity.builder()
                .profile(guild.getProfileInfo().getProfile())
                .name(guild.getProfileInfo().getName())
                .managerId(UserIdJpaVO.make(guild.getManagerId().getId()))
                .inviteCode(guild.getInvitationCode())
                .guildUserEntities(guildUserEntities)
                .build();
    }
}

package harmony.communityservice.guild.guild.adapter.out.persistence;

import harmony.communityservice.guild.guild.domain.GuildRead;
import harmony.communityservice.user.adapter.out.persistence.UserIdJpaVO;

class GuildReadEntityMapper {

    static GuildReadEntity convert(GuildRead guildRead) {
        return GuildReadEntity.builder()
                .name(guildRead.getProfileInfo().getName())
                .profile(guildRead.getProfileInfo().getProfile())
                .userNickname(guildRead.getCommonUserInfo().getNickname())
                .userProfile(guildRead.getCommonUserInfo().getProfile())
                .guildId(GuildIdJpaVO.make(guildRead.getGuildId().getId()))
                .userId(UserIdJpaVO.make(guildRead.getUserId().getId()))
                .build();
    }
}

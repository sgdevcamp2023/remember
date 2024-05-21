package harmony.communityservice.guild.guild.adapter.out.persistence;

import harmony.communityservice.common.annotation.PersistenceAdapter;
import harmony.communityservice.common.exception.NotFoundDataException;
import harmony.communityservice.guild.guild.application.port.out.DeleteGuildPort;
import harmony.communityservice.guild.guild.application.port.out.RegisterGuildPort;
import harmony.communityservice.guild.guild.domain.Guild;
import harmony.communityservice.guild.guild.domain.Guild.GuildId;
import harmony.communityservice.user.adapter.out.persistence.UserIdJpaVO;
import harmony.communityservice.user.domain.User.UserId;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
class GuildCommandPersistenceAdapter implements RegisterGuildPort, DeleteGuildPort {

    private final GuildCommandRepository guildCommandRepository;

    @Override
    public Long register(Guild guild) {
        GuildEntity guildEntity = GuildEntityMapper.convert(guild);
        return guildCommandRepository.save(guildEntity).getGuildId().getId();
    }

    @Override
    public Guild join(String code, UserId userId) {
        GuildEntity guildEntity = guildCommandRepository.findByInviteCode(code).orElseThrow(NotFoundDataException::new);
        guildEntity.updateUserIds(GuildUserEntity.make(UserIdJpaVO.make(userId.getId())));
        guildCommandRepository.save(guildEntity);
        return GuildMapper.convert(guildEntity);
    }

    @Override
    public void delete(GuildId guildId, UserId userId) {
        guildCommandRepository.deleteGuildByGuildIdAndManagerId(GuildIdJpaVO.make(guildId.getId()),
                UserIdJpaVO.make(userId.getId()));
        guildCommandRepository.deleteGuildUsersByGuildId(guildId.getId());
    }
}

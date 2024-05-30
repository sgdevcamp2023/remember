package harmony.communityservice.guild.guild.adapter.out.persistence;

import harmony.communityservice.common.annotation.PersistenceAdapter;
import harmony.communityservice.common.exception.NotFoundDataException;
import harmony.communityservice.guild.guild.application.port.out.LoadGuildPort;
import harmony.communityservice.guild.guild.application.port.out.VerifyGuildManagerPort;
import harmony.communityservice.guild.guild.domain.Guild;
import harmony.communityservice.guild.guild.domain.Guild.GuildId;
import harmony.communityservice.user.adapter.out.persistence.UserIdJpaVO;
import harmony.communityservice.user.domain.User.UserId;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
class GuildQueryPersistenceAdapter implements LoadGuildPort, VerifyGuildManagerPort {

    private final GuildQueryRepository guildQueryRepository;

    @Override
    public Guild loadById(GuildId guildId) {
        GuildEntity guildEntity = guildQueryRepository.findById(GuildIdJpaVO.make(guildId.getId()))
                .orElseThrow(NotFoundDataException::new);
        return GuildMapper.convert(guildEntity);
    }

    @Override
    public void verify(GuildId guildId, UserId managerId) {
        if (!guildQueryRepository.existsByGuildIdAndManagerId(GuildIdJpaVO.make(guildId.getId()),
                UserIdJpaVO.make(managerId.getId()))) {
            throw new NotFoundDataException("관리자만 가능합니다");
        }
    }
}

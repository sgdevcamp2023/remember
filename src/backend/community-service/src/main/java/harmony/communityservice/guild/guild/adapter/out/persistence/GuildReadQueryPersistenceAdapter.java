package harmony.communityservice.guild.guild.adapter.out.persistence;

import harmony.communityservice.common.annotation.PersistenceAdapter;
import harmony.communityservice.common.exception.NotFoundDataException;
import harmony.communityservice.guild.guild.application.port.out.LoadGuildIdsPort;
import harmony.communityservice.guild.guild.application.port.out.LoadGuildReadsPort;
import harmony.communityservice.guild.guild.application.port.out.VerifyGuildMemberPort;
import harmony.communityservice.guild.guild.domain.Guild.GuildId;
import harmony.communityservice.guild.guild.domain.GuildRead;
import harmony.communityservice.user.adapter.out.persistence.UserIdJpaVO;
import harmony.communityservice.user.domain.User.UserId;
import java.util.List;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
class GuildReadQueryPersistenceAdapter implements LoadGuildReadsPort, VerifyGuildMemberPort, LoadGuildIdsPort {

    private final GuildReadQueryRepository guildReadQueryRepository;

    @Override
    public List<GuildRead> loadListByGuildId(GuildId guildId) {
        List<GuildReadEntity> guildReadEntities = guildReadQueryRepository.findByGuildId(
                GuildIdJpaVO.make(guildId.getId()));

        return guildReadEntities.stream()
                .map(GuildReadMapper::convert)
                .toList();
    }

    @Override
    public List<GuildRead> loadListByUserId(UserId userId) {
        List<GuildReadEntity> guildReadEntities = guildReadQueryRepository.findGuildReadsByUserId(
                UserIdJpaVO.make(userId.getId()));

        return guildReadEntities.stream()
                .map(GuildReadMapper::convert)
                .toList();
    }

    @Override
    public void verify(UserId userId, GuildId guildId) {
        if (!guildReadQueryRepository.existsByGuildIdAndUserId(GuildIdJpaVO.make(guildId.getId()),
                UserIdJpaVO.make(userId.getId()))) {
            throw new NotFoundDataException();
        }
    }

    @Override
    public List<GuildId> loadList(UserId userId) {
        List<GuildIdJpaVO> guildIds = guildReadQueryRepository.findGuildIdsByUserId(
                UserIdJpaVO.make(userId.getId()));
        return guildIds.stream()
                .map(guildIdJpaVO -> GuildId.make(guildIdJpaVO.getId()))
                .toList();
    }
}

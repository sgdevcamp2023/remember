package harmony.communityservice.guild.guild.adapter.out.persistence;

import harmony.communityservice.common.annotation.PersistenceAdapter;
import harmony.communityservice.common.exception.NotFoundDataException;
import harmony.communityservice.guild.guild.application.port.out.DeleteGuildReadPort;
import harmony.communityservice.guild.guild.application.port.out.LoadGuildReadPort;
import harmony.communityservice.guild.guild.application.port.out.ModifyGuildUserNicknamePort;
import harmony.communityservice.guild.guild.application.port.out.ModifyGuildUserNicknamesPort;
import harmony.communityservice.guild.guild.application.port.out.RegisterGuildReadPort;
import harmony.communityservice.guild.guild.domain.Guild.GuildId;
import harmony.communityservice.guild.guild.domain.GuildRead;
import harmony.communityservice.user.adapter.out.persistence.UserIdJpaVO;
import harmony.communityservice.user.domain.User.UserId;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
class GuildReadCommandPersistenceAdapter implements RegisterGuildReadPort, DeleteGuildReadPort,
        ModifyGuildUserNicknamePort, ModifyGuildUserNicknamesPort, LoadGuildReadPort {

    private final GuildReadCommandRepository guildReadCommandRepository;

    @Override
    public void register(GuildRead guildRead) {
        GuildReadEntity guildReadEntity = GuildReadEntityMapper.convert(guildRead);
        guildReadCommandRepository.save(guildReadEntity);
    }

    @Override
    public void delete(GuildId guildId) {
        guildReadCommandRepository.deleteGuildReadsByGuildId(GuildIdJpaVO.make(guildId.getId()));
    }

    @Override
    public void modifyNickname(GuildId guildId, UserId userId, String nickname) {
        guildReadCommandRepository.updateNickname(nickname, GuildIdJpaVO.make(guildId.getId()),
                UserIdJpaVO.make(userId.getId()));
    }

    @Override
    public void modify(UserId userId, String nickname) {
        guildReadCommandRepository.updateNicknames(nickname, UserIdJpaVO.make(userId.getId()));
    }

    @Override
    public GuildRead loadByUserIdAndGuildId(UserId userId, GuildId guildId) {
        GuildReadEntity guildReadEntity = guildReadCommandRepository.findByGuildIdAndUserId(
                GuildIdJpaVO.make(guildId.getId()), UserIdJpaVO.make(
                        userId.getId())).orElseThrow(NotFoundDataException::new);

        return GuildReadMapper.convert(guildReadEntity);
    }
}

package harmony.communityservice.guild.guild.application.service;

import harmony.communityservice.common.annotation.UseCase;
import harmony.communityservice.guild.guild.application.port.in.DeleteGuildReadUseCase;
import harmony.communityservice.guild.guild.application.port.in.LoadGuildReadUseCase;
import harmony.communityservice.guild.guild.application.port.in.ModifyGuildNicknameCommand;
import harmony.communityservice.guild.guild.application.port.in.ModifyGuildNicknameUseCase;
import harmony.communityservice.guild.guild.application.port.in.ModifyGuildNicknamesUseCase;
import harmony.communityservice.guild.guild.application.port.in.RegisterGuildReadCommand;
import harmony.communityservice.guild.guild.application.port.in.RegisterGuildReadUseCase;
import harmony.communityservice.guild.guild.application.port.out.DeleteGuildReadPort;
import harmony.communityservice.guild.guild.application.port.out.LoadGuildReadPort;
import harmony.communityservice.guild.guild.application.port.out.ModifyGuildUserNicknamePort;
import harmony.communityservice.guild.guild.application.port.out.ModifyGuildUserNicknamesPort;
import harmony.communityservice.guild.guild.application.port.out.RegisterGuildReadPort;
import harmony.communityservice.guild.guild.domain.Guild.GuildId;
import harmony.communityservice.guild.guild.domain.GuildRead;
import harmony.communityservice.user.application.port.in.LoadUserUseCase;
import harmony.communityservice.user.domain.User;
import harmony.communityservice.user.domain.User.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@Transactional
@RequiredArgsConstructor
class GuildReadCommandService implements RegisterGuildReadUseCase, DeleteGuildReadUseCase, ModifyGuildNicknameUseCase,
        ModifyGuildNicknamesUseCase, LoadGuildReadUseCase {

    private final LoadUserUseCase loadUserUseCase;
    private final RegisterGuildReadPort registerGuildReadPort;
    private final DeleteGuildReadPort deleteGuildReadPort;
    private final ModifyGuildUserNicknamePort modifyGuildUserNicknamePort;
    private final ModifyGuildUserNicknamesPort modifyGuildUserNicknamesPort;
    private final LoadGuildReadPort loadGuildReadPort;

    @Override
    public void register(RegisterGuildReadCommand registerGuildReadCommand) {
        User loadUser = loadUserUseCase.loadUser(registerGuildReadCommand.userId());
        GuildRead guildRead = GuildReadMapper.convert(registerGuildReadCommand, loadUser);
        registerGuildReadPort.register(guildRead);
    }

    @Override
    public void delete(long guildId) {
        deleteGuildReadPort.delete(GuildId.make(guildId));
    }

    @Override
    public GuildRead loadByUserIdAndGuildId(Long userId, Long guildId) {
        return loadGuildReadPort.loadByUserIdAndGuildId(UserId.make(userId), GuildId.make(guildId));
    }

    @Override
    public void modifyNickname(ModifyGuildNicknameCommand modifyGuildNicknameCommand) {
        modifyGuildUserNicknamePort.modifyNickname(GuildId.make(modifyGuildNicknameCommand.guildId()),
                UserId.make(modifyGuildNicknameCommand.userId()), modifyGuildNicknameCommand.nickname());
    }

    @Override
    public void modify(Long userId, String nickname) {
        modifyGuildUserNicknamesPort.modify(UserId.make(userId), nickname);
    }
}

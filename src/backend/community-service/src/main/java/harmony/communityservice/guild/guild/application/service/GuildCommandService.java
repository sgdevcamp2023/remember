package harmony.communityservice.guild.guild.application.service;

import harmony.communityservice.common.annotation.UseCase;
import harmony.communityservice.common.event.Events;
import harmony.communityservice.common.event.dto.inner.DeleteCategoryEvent;
import harmony.communityservice.common.event.dto.inner.DeleteChannelEvent;
import harmony.communityservice.common.event.dto.inner.DeleteGuildReadEvent;
import harmony.communityservice.common.event.dto.inner.RegisterChannelEvent;
import harmony.communityservice.common.service.FileConverter;
import harmony.communityservice.guild.guild.application.port.in.DeleteGuildCommand;
import harmony.communityservice.guild.guild.application.port.in.DeleteGuildUseCase;
import harmony.communityservice.guild.guild.application.port.in.JoinGuildCommand;
import harmony.communityservice.guild.guild.application.port.in.JoinGuildUseCase;
import harmony.communityservice.guild.guild.application.port.in.RegisterGuildCommand;
import harmony.communityservice.guild.guild.application.port.in.RegisterGuildUseCase;
import harmony.communityservice.guild.guild.application.port.out.DeleteGuildPort;
import harmony.communityservice.guild.guild.application.port.out.RegisterGuildPort;
import harmony.communityservice.guild.guild.domain.Guild;
import harmony.communityservice.guild.guild.domain.Guild.GuildId;
import harmony.communityservice.user.domain.User.UserId;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@Transactional
@RequiredArgsConstructor
class GuildCommandService implements RegisterGuildUseCase, JoinGuildUseCase, DeleteGuildUseCase {

    private final FileConverter fileConverter;
    private final RegisterGuildPort registerGuildPort;
    private final DeleteGuildPort deleteGuildPort;

    @Override
    public Long register(RegisterGuildCommand registerGuildCommand) {
        Guild guild = createGuild(registerGuildCommand);
        Long guildId = registerGuildPort.register(guild);
        Events.send(new RegisterChannelEvent(
                guildId, "기본채널", registerGuildCommand.managerId(), 0L, "TEXT"));
        registerGuildRead(registerGuildCommand.managerId(), guild);
        return guildId;
    }

    @Override
    public void join(JoinGuildCommand joinGuildCommand) {
        List<String> parsedInvitationCodes = List.of(
                joinGuildCommand.invitationCode().split("\\."));
        Guild guild = registerGuildPort.join(parsedInvitationCodes.get(0), UserId.make(joinGuildCommand.userId()));
        registerGuildRead(joinGuildCommand.userId(), guild);
    }

    private Guild createGuild(RegisterGuildCommand registerGuildCommand) {
        String uploadedImageUrl = fileConverter.fileToUrl(registerGuildCommand.file());
        return GuildMapper.convert(registerGuildCommand, uploadedImageUrl);
    }

    private void registerGuildRead(Long userId, Guild guild) {
        Events.send(RegisterGuildReadEventMapper.convert(guild, userId));
        Events.send(GuildCreatedEventMapper.convert(guild));
    }

    @Override
    public void delete(DeleteGuildCommand deleteGuildCommand) {
        deleteGuildPort.delete(GuildId.make(deleteGuildCommand.guildId()), UserId.make(deleteGuildCommand.userId()));
        Events.send(GuildDeletedEventMapper.convert(deleteGuildCommand.guildId()));
        Events.send(new DeleteGuildReadEvent(deleteGuildCommand.guildId()));
        Events.send(new DeleteCategoryEvent(deleteGuildCommand.guildId()));
        Events.send(new DeleteChannelEvent(deleteGuildCommand.guildId()));
    }
}

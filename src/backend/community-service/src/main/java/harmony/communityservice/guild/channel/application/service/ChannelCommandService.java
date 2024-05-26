package harmony.communityservice.guild.channel.application.service;

import harmony.communityservice.common.annotation.AuthorizeGuildMember;
import harmony.communityservice.common.annotation.UseCase;
import harmony.communityservice.common.event.Events;
import harmony.communityservice.common.event.dto.inner.DeleteBoardsEvent;
import harmony.communityservice.common.event.dto.inner.DeleteBoardsInGuildEvent;
import harmony.communityservice.guild.channel.application.port.in.DeleteChannelCommand;
import harmony.communityservice.guild.channel.application.port.in.DeleteChannelUseCase;
import harmony.communityservice.guild.channel.application.port.in.DeleteGuildChannelsUseCase;
import harmony.communityservice.guild.channel.application.port.in.RegisterChannelCommand;
import harmony.communityservice.guild.channel.application.port.in.RegisterChannelUseCase;
import harmony.communityservice.guild.channel.application.port.out.DeleteChannelPort;
import harmony.communityservice.guild.channel.application.port.out.DeleteGuildChannelsPort;
import harmony.communityservice.guild.channel.application.port.out.LoadForumChannelIdsPort;
import harmony.communityservice.guild.channel.application.port.out.RegisterChannelPort;
import harmony.communityservice.guild.channel.domain.Channel;
import harmony.communityservice.guild.channel.domain.Channel.ChannelId;
import harmony.communityservice.guild.channel.domain.ChannelType;
import harmony.communityservice.guild.guild.domain.Guild.GuildId;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@Transactional
@RequiredArgsConstructor
class ChannelCommandService implements RegisterChannelUseCase, DeleteChannelUseCase, DeleteGuildChannelsUseCase {

    private final RegisterChannelPort registerChannelPort;
    private final DeleteChannelPort deleteChannelPort;
    private final LoadForumChannelIdsPort loadForumChannelIdsPort;
    private final DeleteGuildChannelsPort deleteGuildChannelsPort;

    @Override
    @AuthorizeGuildMember
    public void register(RegisterChannelCommand registerChannelCommand) {
        Channel channel = ChannelMapper.convert(registerChannelCommand);
        ChannelId channelId = registerChannelPort.register(channel);
        Events.send(ChannelCreatedEventMapper.convert(channel, channelId));
    }

    @Override
    public void delete(DeleteChannelCommand deleteChannelCommand) {
        deleteChannelPort.deleteById(ChannelId.make(deleteChannelCommand.channelId()));
        sendChannelDeletedEvent(deleteChannelCommand.channelId(), deleteChannelCommand.guildId());
        if (deleteChannelCommand.type().equals("FORUM")) {
            Events.send(new DeleteBoardsEvent(deleteChannelCommand.channelId(), deleteChannelCommand.userId()));
        }
    }

    private void sendChannelDeletedEvent(Long channelId, Long guildId) {
        Events.send(ChannelDeletedEventMapper.convert(channelId, guildId));
    }

    @Override
    public void deleteByGuildId(Long guildId) {
        List<ChannelId> channelIds = loadForumChannelIdsPort.loadForumChannelIds(GuildId.make(guildId),
                ChannelType.FORUM);
        Events.send(new DeleteBoardsInGuildEvent(channelIds));
        deleteGuildChannelsPort.deleteByGuildId(GuildId.make(guildId));
        for (ChannelId channelId : channelIds) {
            Events.send(ChannelDeletedEventMapper.convert(channelId.getId(), guildId));
        }
    }
}

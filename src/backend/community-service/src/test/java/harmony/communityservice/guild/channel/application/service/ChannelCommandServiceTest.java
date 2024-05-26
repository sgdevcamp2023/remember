package harmony.communityservice.guild.channel.application.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;

import harmony.communityservice.common.event.Events;
import harmony.communityservice.common.event.dto.inner.DeleteBoardsEvent;
import harmony.communityservice.common.event.dto.inner.DeleteBoardsInGuildEvent;
import harmony.communityservice.common.event.dto.produce.ChannelCreatedEvent;
import harmony.communityservice.common.event.dto.produce.ChannelDeletedEvent;
import harmony.communityservice.domain.Threshold;
import harmony.communityservice.guild.category.domain.Category.CategoryId;
import harmony.communityservice.guild.channel.application.port.in.DeleteChannelCommand;
import harmony.communityservice.guild.channel.application.port.in.RegisterChannelCommand;
import harmony.communityservice.guild.channel.application.port.out.DeleteChannelPort;
import harmony.communityservice.guild.channel.application.port.out.DeleteGuildChannelsPort;
import harmony.communityservice.guild.channel.application.port.out.LoadForumChannelIdsPort;
import harmony.communityservice.guild.channel.application.port.out.RegisterChannelPort;
import harmony.communityservice.guild.channel.domain.Channel;
import harmony.communityservice.guild.channel.domain.Channel.ChannelId;
import harmony.communityservice.guild.channel.domain.ChannelType;
import harmony.communityservice.guild.guild.domain.Guild.GuildId;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

@ExtendWith(MockitoExtension.class)
class ChannelCommandServiceTest {

    @Mock
    RegisterChannelPort registerChannelPort;
    @Mock
    DeleteChannelPort deleteChannelPort;
    @Mock
    LoadForumChannelIdsPort loadForumChannelIdsPort;
    @Mock
    DeleteGuildChannelsPort deleteGuildChannelsPort;
    @Mock
    ApplicationEventPublisher publisher;
    ChannelCommandService channelCommandService;

    @BeforeEach
    void setting() {
        channelCommandService = new ChannelCommandService(registerChannelPort, deleteChannelPort,
                loadForumChannelIdsPort, deleteGuildChannelsPort);
        Events.register(publisher);
    }

    @Test
    @DisplayName("채널 등록 테스트")
    void register_channel() {
        assertNotNull(channelCommandService);

        Channel channel = Channel.builder()
                .channelId(ChannelId.make(Threshold.MIN.getValue()))
                .name("test_channel")
                .type("FORUM")
                .categoryId(CategoryId.make(1L))
                .guildId(GuildId.make(1L))
                .build();
        ChannelId channelId = ChannelId.make(1L);
        RegisterChannelCommand registerChannelCommand = RegisterChannelCommand.builder()
                .guildId(1L)
                .categoryId(1L)
                .userId(1L)
                .name("test_channel")
                .type("FORUM")
                .build();
        ChannelCreatedEvent channelCreatedEvent = ChannelCreatedEvent.builder()
                .type("CREATE-CHANNEL")
                .guildId(channel.getGuildId().getId())
                .channelType(channel.getType())
                .channelName(channel.getName())
                .channelId(channelId.getId())
                .categoryId(channel.getCategoryId().getId())
                .build();
        given(registerChannelPort.register(channel)).willReturn(channelId);
        channelCommandService.register(registerChannelCommand);

        then(registerChannelPort).should(times(1)).register(channel);
        then(publisher).should(times(1)).publishEvent(channelCreatedEvent);
    }

    @Test
    @DisplayName("채널 삭제 테스트")
    void delete_channel() {
        willDoNothing().given(deleteChannelPort).deleteById(ChannelId.make(1L));
        DeleteChannelCommand deleteChannelCommand = DeleteChannelCommand.builder()
                .userId(1L)
                .channelId(1L)
                .guildId(1L)
                .type("FORUM")
                .build();
        ChannelDeletedEvent channelDeletedEvent = ChannelDeletedEvent
                .builder()
                .type("DELETE-CHANNEL")
                .channelId(1L)
                .guildId(1L)
                .build();
        channelCommandService.delete(deleteChannelCommand);

        then(deleteChannelPort).should(times(1)).deleteById(ChannelId.make(1L));
        then(publisher).should(times(1))
                .publishEvent(new DeleteBoardsEvent(deleteChannelCommand.channelId(), deleteChannelCommand.userId()));
        then(publisher).should(times(1)).publishEvent(channelDeletedEvent);
    }

    @Test
    @DisplayName("길드 채널 삭제 테스트")
    void delete_guild_channel() {
        ChannelDeletedEvent first = ChannelDeletedEvent
                .builder()
                .type("DELETE-CHANNEL")
                .channelId(1L)
                .guildId(1L)
                .build();
        ChannelDeletedEvent second = ChannelDeletedEvent
                .builder()
                .type("DELETE-CHANNEL")
                .channelId(2L)
                .guildId(1L)
                .build();
        ChannelDeletedEvent third = ChannelDeletedEvent
                .builder()
                .type("DELETE-CHANNEL")
                .channelId(3L)
                .guildId(1L)
                .build();
        List<ChannelId> channelIds = List.of(ChannelId.make(1L), ChannelId.make(2L), ChannelId.make(3L));
        given(loadForumChannelIdsPort.loadForumChannelIds(GuildId.make(1L), ChannelType.FORUM)).willReturn(channelIds);
        willDoNothing().given(deleteGuildChannelsPort).deleteByGuildId(GuildId.make(1L));

        channelCommandService.deleteByGuildId(1L);

        then(publisher).should(times(1)).publishEvent(new DeleteBoardsInGuildEvent(channelIds));
        then(publisher).should(times(1)).publishEvent(first);
        then(publisher).should(times(1)).publishEvent(second);
        then(publisher).should(times(1)).publishEvent(third);
    }
}
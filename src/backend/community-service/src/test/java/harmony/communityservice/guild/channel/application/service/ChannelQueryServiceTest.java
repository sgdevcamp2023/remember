package harmony.communityservice.guild.channel.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

import harmony.communityservice.guild.category.domain.Category.CategoryId;
import harmony.communityservice.guild.channel.application.port.in.LoadChannelResponse;
import harmony.communityservice.guild.channel.application.port.in.LoadChannelsCommand;
import harmony.communityservice.guild.channel.application.port.out.LoadChannelsPort;
import harmony.communityservice.guild.channel.domain.Channel;
import harmony.communityservice.guild.channel.domain.Channel.ChannelId;
import harmony.communityservice.guild.guild.domain.Guild.GuildId;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ChannelQueryServiceTest {

    @Mock
    LoadChannelsPort loadChannelsPort;
    ChannelQueryService channelQueryService;

    @BeforeEach
    void setting() {
        channelQueryService = new ChannelQueryService(loadChannelsPort);
    }

    @Test
    @DisplayName("채널 조회 테스트")
    void load_channels() {
        assertNotNull(channelQueryService);
        Channel first = Channel.builder()
                .channelId(ChannelId.make(1L))
                .name("first_channel")
                .type("FORUM")
                .categoryId(CategoryId.make(1L))
                .guildId(GuildId.make(1L))
                .build();
        Channel second = Channel.builder()
                .channelId(ChannelId.make(2L))
                .name("second_channel")
                .type("FORUM")
                .categoryId(CategoryId.make(1L))
                .guildId(GuildId.make(1L))
                .build();
        Channel third = Channel.builder()
                .channelId(ChannelId.make(3L))
                .name("third_channel")
                .type("FORUM")
                .categoryId(CategoryId.make(1L))
                .guildId(GuildId.make(1L))
                .build();
        List<Channel> channels = List.of(first, second, third);
        LoadChannelResponse firstResponse = LoadChannelResponse.builder()
                .channelId(first.getChannelId().getId())
                .channelName(first.getName())
                .channelType(first.getType())
                .categoryId(first.getCategoryId().getId())
                .guildId(first.getGuildId().getId())
                .build();

        LoadChannelResponse secondResponse = LoadChannelResponse.builder()
                .channelId(second.getChannelId().getId())
                .channelName(second.getName())
                .channelType(second.getType())
                .categoryId(second.getCategoryId().getId())
                .guildId(second.getGuildId().getId())
                .build();

        LoadChannelResponse thirdResponse = LoadChannelResponse.builder()
                .channelId(third.getChannelId().getId())
                .channelName(third.getName())
                .channelType(third.getType())
                .categoryId(third.getCategoryId().getId())
                .guildId(third.getGuildId().getId())
                .build();

        given(loadChannelsPort.loadChannels(GuildId.make(1L))).willReturn(channels);

        Map<Long, LoadChannelResponse> resultMap = Map.of(1L, firstResponse, 2L, secondResponse, 3L,
                thirdResponse);

        Map<Long, LoadChannelResponse> responseMap = channelQueryService.loadChannels(
                new LoadChannelsCommand(1L, 1L));

        assertEquals(resultMap, responseMap);
        then(loadChannelsPort).should(times(1)).loadChannels(GuildId.make(1L));
    }

}
package harmony.communityservice.guild.channel.domain;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import harmony.communityservice.common.exception.NotFoundDataException;
import harmony.communityservice.guild.category.domain.Category.CategoryId;
import harmony.communityservice.guild.channel.domain.Channel.ChannelId;
import harmony.communityservice.guild.guild.domain.Guild.GuildId;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ChannelTest {

    @Test
    @DisplayName("같은 식별자면 같은 객체로 인식 테스트")
    void same_channel() {
        Channel firstChannel = Channel.builder()
                .name("test")
                .type("FORUM")
                .channelId(ChannelId.make(1L))
                .categoryId(CategoryId.make(1L))
                .guildId(GuildId.make(1L))
                .build();

        Channel secondChannel = Channel.builder()
                .name("test2")
                .type("FORUM")
                .channelId(ChannelId.make(1L))
                .categoryId(CategoryId.make(2L))
                .guildId(GuildId.make(2L))
                .build();

        boolean equals = firstChannel.equals(secondChannel);

        assertSame(true, equals);
    }

    @Test
    @DisplayName("다른 식별자면 다른 객체로 인식 테스트")
    void different_channel() {
        Channel firstChannel = Channel.builder()
                .name("test")
                .type("FORUM")
                .channelId(ChannelId.make(1L))
                .categoryId(CategoryId.make(1L))
                .guildId(GuildId.make(1L))
                .build();

        Channel secondChannel = Channel.builder()
                .name("test")
                .type("FORUM")
                .channelId(ChannelId.make(2L))
                .categoryId(CategoryId.make(1L))
                .guildId(GuildId.make(1L))
                .build();

        boolean equals = firstChannel.equals(secondChannel);

        assertSame(false, equals);
    }


    @Test
    @DisplayName("name이 없으면 예외 처리 테스트")
    void not_exists_name() {
        assertThrows(NotFoundDataException.class, ()->Channel.builder()
                .type("FORUM")
                .channelId(ChannelId.make(1L))
                .categoryId(CategoryId.make(1L))
                .guildId(GuildId.make(1L))
                .build());
    }

    @Test
    @DisplayName("type이 없으면 예외 처리 테스트")
    void not_exists_type() {
        assertThrows(NotFoundDataException.class, ()->Channel.builder()
                .name("test")
                .channelId(ChannelId.make(1L))
                .categoryId(CategoryId.make(1L))
                .guildId(GuildId.make(1L))
                .build());
    }

    @Test
    @DisplayName("guildId가 없으면 예외 처리 테스트")
    void not_exists_guild_id() {
        assertThrows(NotFoundDataException.class, ()->Channel.builder()
                .name("test")
                .type("FORUM")
                .channelId(ChannelId.make(1L))
                .categoryId(CategoryId.make(1L))
                .build());
    }

    @ParameterizedTest
    @DisplayName("type 정보가 일치할 때 Channel 생성 테스트")
    @ValueSource(strings = {"TEXT", "VOICE", "FORUM", "ANNOUNCEMENT", "STAGE"})
    void same_type(String type) {
        Channel channel = Channel.builder()
                .name("test")
                .type(type)
                .channelId(ChannelId.make(2L))
                .categoryId(CategoryId.make(1L))
                .guildId(GuildId.make(1L))
                .build();

        ChannelType channelType = channel.getType();
        Assertions.assertThat(channelType).isEqualTo(ChannelType.valueOf(type));
    }

    @ParameterizedTest
    @DisplayName("type 정보가 불일치할 때 예외 처리 테스트")
    @ValueSource(strings = {"text", "voice", "forum", "announcement", "stage","TEXt", "VOICe", "FOUM", "ANNOUNCE", "STAGING"})
    void different_type(String type) {
        assertThrows(IllegalArgumentException.class,()->Channel.builder()
                .name("test")
                .type(type)
                .channelId(ChannelId.make(2L))
                .categoryId(CategoryId.make(1L))
                .guildId(GuildId.make(1L))
                .build());

    }
}
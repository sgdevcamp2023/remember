package harmony.communityservice.guild.channel.adapter.out.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import harmony.communityservice.common.exception.NotFoundDataException;
import harmony.communityservice.guild.category.domain.Category.CategoryId;
import harmony.communityservice.guild.channel.domain.Channel;
import harmony.communityservice.guild.channel.domain.Channel.ChannelId;
import harmony.communityservice.guild.channel.domain.ChannelType;
import harmony.communityservice.guild.guild.adapter.out.persistence.GuildIdJpaVO;
import harmony.communityservice.guild.guild.domain.Guild.GuildId;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
@EnableTransactionManagement
@Sql("ChannelCommandPersistenceAdapterTest.sql")
class ChannelCommandPersistenceAdapterTest {

    @Autowired
    ChannelCommandPersistenceAdapter channelCommandPersistenceAdapter;

    @Autowired
    ChannelCommandRepository channelCommandRepository;

    @Test
    @DisplayName("채널 등록 테스트")
    void register_channel() {
        Channel channel = Channel.builder()
                .name("test_channel")
                .type("FORUM")
                .categoryId(CategoryId.make(1L))
                .guildId(GuildId.make(1L))
                .build();

        ChannelId channelId = channelCommandPersistenceAdapter.register(channel);

        ChannelEntity channelEntity = channelCommandRepository.findById(ChannelIdJpaVO.make(channelId.getId())).get();

        assertEquals(channelEntity.getChannelId().getId(), channelId.getId());
        assertEquals(channelEntity.getName(), channel.getName());
    }

    @Test
    @DisplayName("채널 삭제 테스트")
    void delete_channel() {
        channelCommandPersistenceAdapter.deleteById(ChannelId.make(1L));

        assertThrows(NotFoundDataException.class, () -> channelCommandRepository.findById(ChannelIdJpaVO.make(1L))
                .orElseThrow(NotFoundDataException::new));
    }

    @Test
    @DisplayName("길드 내 FORUM 채널 아이디 리스트 조회 테스트")
    void load_forum_channel_ids() {
        List<ChannelId> channelIds = channelCommandPersistenceAdapter.loadForumChannelIds(GuildId.make(1L),
                ChannelType.FORUM);
        List<ChannelIdJpaVO> channelIdJpaVOS = channelCommandRepository.findChannelIdsByGuildIdAndType(
                GuildIdJpaVO.make(1L), ChannelTypeJpaEnum.FORUM);

        assertEquals(3L, channelIds.size());
        assertEquals(channelIds.size(), channelIdJpaVOS.size());
    }

    @Test
    @DisplayName("길드 내 채널 모두 삭제 테스트")
    void delete_channels() {
        channelCommandPersistenceAdapter.deleteByGuildId(GuildId.make(1L));

        List<ChannelId> channelIds = channelCommandPersistenceAdapter.loadForumChannelIds(GuildId.make(1L),
                ChannelType.FORUM);
        List<ChannelIdJpaVO> channelIdJpaVOS = channelCommandRepository.findChannelIdsByGuildIdAndType(
                GuildIdJpaVO.make(1L), ChannelTypeJpaEnum.FORUM);

        assertEquals(0L, channelIds.size());
        assertEquals(channelIds.size(),channelIdJpaVOS.size());
    }

}
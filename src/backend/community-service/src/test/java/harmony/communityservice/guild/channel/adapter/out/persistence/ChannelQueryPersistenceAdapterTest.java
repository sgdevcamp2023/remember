package harmony.communityservice.guild.channel.adapter.out.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;

import harmony.communityservice.guild.channel.domain.Channel;
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
@Sql("ChannelQueryPersistenceAdapterTest.sql")
class ChannelQueryPersistenceAdapterTest {

    @Autowired
    ChannelQueryPersistenceAdapter channelQueryPersistenceAdapter;

    @Autowired
    ChannelQueryRepository channelQueryRepository;

    @Test
    @DisplayName("길드 내의 채널 리스트 조회 테스트")
    void load_channels() {
        List<Channel> channels = channelQueryPersistenceAdapter.loadChannels(GuildId.make(1L));
        List<ChannelEntity> channelEntities = channelQueryRepository.findChannelsByGuildId(GuildIdJpaVO.make(1L));

        assertEquals(channels.size(), channelEntities.size());
        assertEquals(channels.size(), 3L);
    }
}
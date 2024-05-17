package harmony.communityservice.common.event.dto.inner;

import harmony.communityservice.guild.channel.adapter.out.persistence.ChannelIdJpaVO;
import java.util.List;

public record DeleteBoardsInGuildEvent(List<ChannelIdJpaVO> channelIds) {
}

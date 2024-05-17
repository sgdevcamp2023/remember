package harmony.communityservice.common.event.dto.inner;

import harmony.communityservice.guild.channel.domain.Channel.ChannelId;
import java.util.List;

public record DeleteBoardsInGuildEvent(List<ChannelId> channelIds) {
}

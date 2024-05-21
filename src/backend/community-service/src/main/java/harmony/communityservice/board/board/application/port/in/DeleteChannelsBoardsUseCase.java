package harmony.communityservice.board.board.application.port.in;

import harmony.communityservice.guild.channel.domain.Channel.ChannelId;
import java.util.List;

public interface DeleteChannelsBoardsUseCase {

    void deleteInChannels(List<ChannelId> channelIds);
}

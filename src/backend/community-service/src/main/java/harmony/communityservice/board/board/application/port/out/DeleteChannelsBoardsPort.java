package harmony.communityservice.board.board.application.port.out;

import harmony.communityservice.board.board.domain.Board.BoardId;
import harmony.communityservice.guild.channel.domain.Channel.ChannelId;
import java.util.List;

public interface DeleteChannelsBoardsPort {

    List<BoardId> deleteInChannels(List<ChannelId> channelIds);
}

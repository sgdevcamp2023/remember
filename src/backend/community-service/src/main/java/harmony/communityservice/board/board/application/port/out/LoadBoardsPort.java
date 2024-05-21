package harmony.communityservice.board.board.application.port.out;

import harmony.communityservice.board.board.domain.Board;
import harmony.communityservice.board.board.domain.Board.BoardId;
import harmony.communityservice.guild.channel.domain.Channel.ChannelId;
import java.util.List;
import org.springframework.data.domain.PageRequest;

public interface LoadBoardsPort {
    List<Board> loadBoards(ChannelId channelId, BoardId boardId, PageRequest pageRequest);
}

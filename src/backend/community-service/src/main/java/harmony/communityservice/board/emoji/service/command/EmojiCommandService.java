package harmony.communityservice.board.emoji.service.command;

import harmony.communityservice.board.emoji.dto.DeleteEmojiRequest;
import harmony.communityservice.board.emoji.dto.RegisterEmojiRequest;
import java.util.List;

public interface EmojiCommandService {

    void register(RegisterEmojiRequest registerEmojiRequest);

    void delete(DeleteEmojiRequest deleteEmojiRequest);

    void deleteListByBoardId(Long boardId);

    void deleteListByBoardIds(List<Long> boardIds);
}

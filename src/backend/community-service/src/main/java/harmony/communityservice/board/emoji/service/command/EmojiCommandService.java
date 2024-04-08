package harmony.communityservice.board.emoji.service.command;

import harmony.communityservice.board.emoji.dto.DeleteEmojiRequest;
import harmony.communityservice.board.emoji.dto.RegisterEmojiRequest;

public interface EmojiCommandService {

    void register(RegisterEmojiRequest registerEmojiRequest);

    void delete(DeleteEmojiRequest deleteEmojiRequest);
}

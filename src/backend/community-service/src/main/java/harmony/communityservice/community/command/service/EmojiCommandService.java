package harmony.communityservice.community.command.service;

import harmony.communityservice.community.command.dto.DeleteEmojiRequest;
import harmony.communityservice.community.command.dto.RegisterEmojiRequest;
import org.springframework.transaction.annotation.Transactional;

public interface EmojiCommandService {

    void register(RegisterEmojiRequest registerEmojiRequest);

    void delete(DeleteEmojiRequest deleteEmojiRequest);
}

package harmony.communityservice.community.command.service;

import harmony.communityservice.community.command.dto.EmojiDeleteRequestDto;
import harmony.communityservice.community.command.dto.EmojiRegistrationRequestDto;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface EmojiCommandService {

    void save(EmojiRegistrationRequestDto emojiRegistrationRequestDto);

    void delete(EmojiDeleteRequestDto emojiDeleteRequestDto);
}

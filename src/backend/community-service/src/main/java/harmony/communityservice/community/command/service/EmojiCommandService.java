package harmony.communityservice.community.command.service;

import harmony.communityservice.community.command.dto.EmojiRegistrationRequestDto;

public interface EmojiCommandService {

    void save(EmojiRegistrationRequestDto emojiRegistrationRequestDto);
}

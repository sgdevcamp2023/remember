package harmony.communityservice.community.command.service.impl;

import harmony.communityservice.community.command.repository.EmojiUserCommandRepository;
import harmony.communityservice.community.command.service.EmojiUserCommandService;
import harmony.communityservice.community.domain.Emoji;
import harmony.communityservice.community.domain.EmojiUser;
import harmony.communityservice.community.mapper.ToEmojiUserMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EmojiUserCommandServiceImpl implements EmojiUserCommandService {

    private final EmojiUserCommandRepository emojiUserCommandRepository;

    @Override
    public void register(Emoji emoji, Long userId) {
        EmojiUser emojiUser = ToEmojiUserMapper.convert(emoji, userId);
        emojiUserCommandRepository.save(emojiUser);
    }

    @Override
    public void delete(EmojiUser emojiUser) {
        emojiUserCommandRepository.delete(emojiUser);
    }
}

package harmony.communityservice.board.config;

import harmony.communityservice.board.board.repository.command.BoardCommandRepository;
import harmony.communityservice.board.board.repository.command.impl.BoardCommandRepositoryImpl;
import harmony.communityservice.board.board.repository.command.jpa.JpaBoardCommandRepository;
import harmony.communityservice.board.board.service.command.BoardCommandService;
import harmony.communityservice.board.board.service.command.impl.BoardCommandServiceImpl;
import harmony.communityservice.board.board.service.query.BoardQueryService;
import harmony.communityservice.board.comment.service.command.CommentCommandService;
import harmony.communityservice.board.comment.service.command.impl.CommentCommandServiceImpl;
import harmony.communityservice.board.emoji.repository.command.EmojiCommandRepository;
import harmony.communityservice.board.emoji.repository.command.impl.EmojiCommandRepositoryImpl;
import harmony.communityservice.board.emoji.repository.command.jpa.JpaEmojiCommandRepository;
import harmony.communityservice.board.emoji.service.command.EmojiCommandService;
import harmony.communityservice.board.emoji.service.command.impl.EmojiCommandServiceImpl;
import harmony.communityservice.board.emoji.service.query.EmojiQueryService;
import harmony.communityservice.common.service.ContentService;
import harmony.communityservice.user.service.command.UserReadCommandService;
import harmony.communityservice.user.service.query.UserReadQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BoardCommandConfig {

    private final JpaEmojiCommandRepository jpaEmojiCommandRepository;
    private final EmojiQueryService emojiQueryService;
    private final JpaBoardCommandRepository jpaBoardCommandRepository;
    private final ContentService contentService;
    private final UserReadCommandService userReadCommandService;
    private final BoardQueryService boardQueryService;


    @Bean
    public CommentCommandService commentCommandService() {
        return new CommentCommandServiceImpl(boardQueryService, boardCommandRepository());
    }

    @Bean
    public EmojiCommandRepository emojiCommandRepository() {
        return new EmojiCommandRepositoryImpl(jpaEmojiCommandRepository);
    }

    @Bean
    public EmojiCommandService emojiCommandService() {
        return new EmojiCommandServiceImpl(emojiCommandRepository(), emojiQueryService);
    }

    @Bean
    public BoardCommandRepository boardCommandRepository() {
        return new BoardCommandRepositoryImpl(jpaBoardCommandRepository);
    }

    @Bean
    public BoardCommandService boardCommandService() {
        return new BoardCommandServiceImpl(contentService, userReadCommandService, boardCommandRepository(),
                boardQueryService);
    }
}

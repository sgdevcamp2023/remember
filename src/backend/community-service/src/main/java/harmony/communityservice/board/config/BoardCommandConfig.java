package harmony.communityservice.board.config;

import harmony.communityservice.board.board.repository.command.BoardCommandRepository;
import harmony.communityservice.board.board.repository.command.impl.BoardCommandRepositoryImpl;
import harmony.communityservice.board.board.repository.command.jpa.JpaBoardCommandRepository;
import harmony.communityservice.board.board.service.command.BoardCommandService;
import harmony.communityservice.board.board.service.command.impl.BoardCommandServiceImpl;
import harmony.communityservice.board.comment.repository.command.CommentCommandRepository;
import harmony.communityservice.board.comment.repository.command.impl.CommentCommandRepositoryImpl;
import harmony.communityservice.board.comment.repository.command.jpa.JpaCommentCommandRepository;
import harmony.communityservice.board.comment.service.command.CommentCommandService;
import harmony.communityservice.board.comment.service.command.impl.CommentCommandServiceImpl;
import harmony.communityservice.board.emoji.repository.command.EmojiCommandRepository;
import harmony.communityservice.board.emoji.repository.command.impl.EmojiCommandRepositoryImpl;
import harmony.communityservice.board.emoji.repository.command.jpa.JpaEmojiCommandRepository;
import harmony.communityservice.board.emoji.service.command.EmojiCommandService;
import harmony.communityservice.board.emoji.service.command.impl.EmojiCommandServiceImpl;
import harmony.communityservice.common.service.FileConverter;
import harmony.communityservice.user.service.command.UserReadCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BoardCommandConfig {

    private final JpaEmojiCommandRepository jpaEmojiCommandRepository;
    private final JpaBoardCommandRepository jpaBoardCommandRepository;
    private final FileConverter contentService;
    private final UserReadCommandService userReadCommandService;
    private final JpaCommentCommandRepository jpaCommentCommandRepository;


    @Bean
    public CommentCommandRepository commentCommandRepository() {
        return new CommentCommandRepositoryImpl(jpaCommentCommandRepository);
    }

    @Bean
    public CommentCommandService commentCommandService() {
        return new CommentCommandServiceImpl(commentCommandRepository());
    }

    @Bean
    public EmojiCommandRepository emojiCommandRepository() {
        return new EmojiCommandRepositoryImpl(jpaEmojiCommandRepository);
    }

    @Bean
    public EmojiCommandService emojiCommandService() {
        return new EmojiCommandServiceImpl(emojiCommandRepository());
    }

    @Bean
    public BoardCommandRepository boardCommandRepository() {
        return new BoardCommandRepositoryImpl(jpaBoardCommandRepository);
    }

    @Bean
    public BoardCommandService boardCommandService() {
        return new BoardCommandServiceImpl(contentService, userReadCommandService, boardCommandRepository());
    }
}

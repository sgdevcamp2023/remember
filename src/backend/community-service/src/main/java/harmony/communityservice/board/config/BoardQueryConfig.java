package harmony.communityservice.board.config;


import harmony.communityservice.board.board.repository.query.BoardQueryRepository;
import harmony.communityservice.board.board.repository.query.impl.BoardQueryRepositoryImpl;
import harmony.communityservice.board.board.repository.query.jpa.JpaBoardQueryRepository;
import harmony.communityservice.board.board.service.query.BoardQueryService;
import harmony.communityservice.board.board.service.query.impl.BoardQueryServiceImpl;
import harmony.communityservice.board.comment.repository.query.CommentQueryRepository;
import harmony.communityservice.board.comment.repository.query.impl.CommentQueryRepositoryImpl;
import harmony.communityservice.board.comment.repository.query.jpa.JpaCommentQueryRepository;
import harmony.communityservice.board.comment.service.query.CommentQueryService;
import harmony.communityservice.board.comment.service.query.impl.CommentQueryServiceImpl;
import harmony.communityservice.board.emoji.repository.query.EmojiQueryRepository;
import harmony.communityservice.board.emoji.repository.query.impl.EmojiQueryRepositoryImpl;
import harmony.communityservice.board.emoji.repository.query.jpa.JpaEmojiQueryRepository;
import harmony.communityservice.board.emoji.service.query.EmojiQueryService;
import harmony.communityservice.board.emoji.service.query.impl.EmojiQueryServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BoardQueryConfig {

    private final JpaEmojiQueryRepository jpaEmojiQueryRepository;
    private final JpaBoardQueryRepository jpaBoardQueryRepository;
    private final JpaCommentQueryRepository jpaCommentQueryRepository;

    @Bean
    public CommentQueryRepository commentQueryRepository() {
        return new CommentQueryRepositoryImpl(jpaCommentQueryRepository);
    }

    @Bean
    public CommentQueryService commentQueryService() {
        return new CommentQueryServiceImpl(commentQueryRepository());
    }

    @Bean
    public EmojiQueryRepository emojiQueryRepository() {
        return new EmojiQueryRepositoryImpl(jpaEmojiQueryRepository);
    }

    @Bean
    public EmojiQueryService emojiQueryService() {
        return new EmojiQueryServiceImpl(emojiQueryRepository());
    }

    @Bean
    public BoardQueryRepository boardQueryRepository() {
        return new BoardQueryRepositoryImpl(jpaBoardQueryRepository);
    }

    @Bean
    public BoardQueryService boardQueryService() {
        return new BoardQueryServiceImpl(boardQueryRepository(), emojiQueryService(), commentQueryService());
    }
}

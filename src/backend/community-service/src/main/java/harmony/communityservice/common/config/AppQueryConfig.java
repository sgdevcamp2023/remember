package harmony.communityservice.common.config;

import harmony.communityservice.common.feign.UserStatusClient;
import harmony.communityservice.community.query.repository.BoardQueryRepository;
import harmony.communityservice.community.query.repository.CategoryQueryRepository;
import harmony.communityservice.community.query.repository.ChannelQueryRepository;
import harmony.communityservice.community.query.repository.CommentQueryRepository;
import harmony.communityservice.community.query.repository.EmojiQueryRepository;
import harmony.communityservice.community.query.repository.GuildQueryRepository;
import harmony.communityservice.community.query.repository.GuildReadQueryRepository;
import harmony.communityservice.community.query.repository.RoomQueryRepository;
import harmony.communityservice.community.query.repository.UserQueryRepository;
import harmony.communityservice.community.query.repository.UserReadQueryRepository;
import harmony.communityservice.community.query.repository.impl.BoardQueryRepositoryImpl;
import harmony.communityservice.community.query.repository.impl.CategoryQueryRepositoryImpl;
import harmony.communityservice.community.query.repository.impl.ChannelQueryRepositoryImpl;
import harmony.communityservice.community.query.repository.impl.CommentQueryRepositoryImpl;
import harmony.communityservice.community.query.repository.impl.EmojiQueryRepositoryImpl;
import harmony.communityservice.community.query.repository.impl.GuildQueryRepositoryImpl;
import harmony.communityservice.community.query.repository.impl.GuildReadQueryRepositoryImpl;
import harmony.communityservice.community.query.repository.impl.RoomQueryRepositoryImpl;
import harmony.communityservice.community.query.repository.impl.UserQueryRepositoryImpl;
import harmony.communityservice.community.query.repository.impl.UserReadQueryRepositoryImpl;
import harmony.communityservice.community.query.repository.jpa.JpaBoardQueryRepository;
import harmony.communityservice.community.query.repository.jpa.JpaCategoryQueryRepository;
import harmony.communityservice.community.query.repository.jpa.JpaChannelQueryRepository;
import harmony.communityservice.community.query.repository.jpa.JpaCommentQueryRepository;
import harmony.communityservice.community.query.repository.jpa.JpaEmojiQueryRepository;
import harmony.communityservice.community.query.repository.jpa.JpaGuildQueryRepository;
import harmony.communityservice.community.query.repository.jpa.JpaGuildReadQueryRepository;
import harmony.communityservice.community.query.repository.jpa.JpaRoomQueryRepository;
import harmony.communityservice.community.query.repository.jpa.JpaUserQueryRepository;
import harmony.communityservice.community.query.repository.jpa.JpaUserReadQueryRepository;
import harmony.communityservice.community.query.service.BoardQueryService;
import harmony.communityservice.community.query.service.CategoryQueryService;
import harmony.communityservice.community.query.service.ChannelQueryService;
import harmony.communityservice.community.query.service.CommentQueryService;
import harmony.communityservice.community.query.service.EmojiQueryService;
import harmony.communityservice.community.query.service.GuildQueryService;
import harmony.communityservice.community.query.service.GuildReadQueryService;
import harmony.communityservice.community.query.service.RoomQueryService;
import harmony.communityservice.community.query.service.UserQueryService;
import harmony.communityservice.community.query.service.UserReadQueryService;
import harmony.communityservice.community.query.service.impl.BoardQueryServiceImpl;
import harmony.communityservice.community.query.service.impl.CategoryQueryServiceImpl;
import harmony.communityservice.community.query.service.impl.ChannelQueryServiceImpl;
import harmony.communityservice.community.query.service.impl.CommentQueryServiceImpl;
import harmony.communityservice.community.query.service.impl.EmojiQueryServiceImpl;
import harmony.communityservice.community.query.service.impl.GuildQueryServiceImpl;
import harmony.communityservice.community.query.service.impl.GuildReadQueryServiceImpl;
import harmony.communityservice.community.query.service.impl.RoomQueryServiceImpl;
import harmony.communityservice.community.query.service.impl.UserQueryServiceImpl;
import harmony.communityservice.community.query.service.impl.UserReadQueryServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class AppQueryConfig {

    private final JpaUserQueryRepository jpaUserQueryRepository;
    private final JpaUserReadQueryRepository jpaUserReadQueryRepository;
    private final JpaGuildQueryRepository jpaGuildQueryRepository;
    private final JpaGuildReadQueryRepository jpaGuildReadQueryRepository;
    private final JpaCategoryQueryRepository jpaCategoryQueryRepository;
    private final JpaChannelQueryRepository jpaChannelQueryRepository;
    private final JpaBoardQueryRepository jpaBoardQueryRepository;
    private final JpaCommentQueryRepository jpaCommentQueryRepository;
    private final JpaEmojiQueryRepository jpaEmojiQueryRepository;
    private final JpaRoomQueryRepository jpaRoomQueryRepository;
    private final UserStatusClient userStatusClient;

    @Bean
    public UserQueryRepository userQueryRepository() {
        return new UserQueryRepositoryImpl(jpaUserQueryRepository);
    }

    @Bean
    public UserQueryService userQueryService() {
        return new UserQueryServiceImpl(userQueryRepository());
    }

    @Bean
    public UserReadQueryRepository userReadQueryRepository() {
        return new UserReadQueryRepositoryImpl(jpaUserReadQueryRepository);
    }

    @Bean
    public UserReadQueryService userReadQueryService() {
        return new UserReadQueryServiceImpl(userStatusClient, userReadQueryRepository());
    }

    @Bean
    public GuildQueryRepository guildQueryRepository() {
        return new GuildQueryRepositoryImpl(jpaGuildQueryRepository);
    }

    @Bean
    public GuildQueryService guildQueryService() {
        return new GuildQueryServiceImpl(guildQueryRepository(), userReadQueryService());
    }

    @Bean
    public GuildReadQueryRepository guildReadQueryRepository() {
        return new GuildReadQueryRepositoryImpl(jpaGuildReadQueryRepository);
    }

    @Bean
    public GuildReadQueryService guildReadQueryService() {
        return new GuildReadQueryServiceImpl(guildReadQueryRepository());
    }

    @Bean
    public CategoryQueryRepository categoryQueryRepository() {
        return new CategoryQueryRepositoryImpl(jpaCategoryQueryRepository);
    }

    @Bean
    public CategoryQueryService categoryQueryService() {
        return new CategoryQueryServiceImpl(categoryQueryRepository(), userReadQueryService());
    }

    @Bean
    public ChannelQueryRepository channelQueryRepository() {
        return new ChannelQueryRepositoryImpl(jpaChannelQueryRepository);
    }

    @Bean
    public ChannelQueryService channelQueryService() {
        return new ChannelQueryServiceImpl(channelQueryRepository(), userReadQueryService());
    }

    @Bean
    public BoardQueryRepository boardQueryRepository() {
        return new BoardQueryRepositoryImpl(jpaBoardQueryRepository);
    }

    @Bean
    public BoardQueryService boardQueryService() {
        return new BoardQueryServiceImpl(boardQueryRepository(),commentQueryService());
    }

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
    public RoomQueryRepository roomQueryRepository() {
        return new RoomQueryRepositoryImpl(jpaRoomQueryRepository);
    }

    @Bean
    public RoomQueryService roomQueryService() {
        return new RoomQueryServiceImpl(userQueryService(), roomQueryRepository(), userStatusClient);
    }
}

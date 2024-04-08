package harmony.communityservice.common.config;

import harmony.communityservice.common.service.ContentService;
import harmony.communityservice.common.service.ProducerService;
import harmony.communityservice.community.command.repository.BoardCommandRepository;
import harmony.communityservice.community.command.repository.CategoryCommandRepository;
import harmony.communityservice.community.command.repository.ChannelCommandRepository;
import harmony.communityservice.community.command.repository.CommentCommandRepository;
import harmony.communityservice.community.command.repository.EmojiCommandRepository;
import harmony.communityservice.community.command.repository.GuildCommandRepository;
import harmony.communityservice.community.command.repository.GuildReadCommandRepository;
import harmony.communityservice.community.command.repository.ImageCommandRepository;
import harmony.communityservice.community.command.repository.RoomCommandRepository;
import harmony.communityservice.community.command.repository.UserCommandRepository;
import harmony.communityservice.community.command.repository.UserReadCommandRepository;
import harmony.communityservice.community.command.repository.impl.BoardCommandRepositoryImpl;
import harmony.communityservice.community.command.repository.impl.CategoryCommandRepositoryImpl;
import harmony.communityservice.community.command.repository.impl.ChannelCommandRepositoryImpl;
import harmony.communityservice.community.command.repository.impl.CommentCommandRepositoryImpl;
import harmony.communityservice.community.command.repository.impl.EmojiCommandRepositoryImpl;
import harmony.communityservice.community.command.repository.impl.GuildCommandRepositoryImpl;
import harmony.communityservice.community.command.repository.impl.GuildReadCommandRepositoryImpl;
import harmony.communityservice.community.command.repository.impl.ImageCommandRepositoryImpl;
import harmony.communityservice.community.command.repository.impl.RoomCommandRepositoryImpl;
import harmony.communityservice.community.command.repository.impl.UserCommandRepositoryImpl;
import harmony.communityservice.community.command.repository.impl.UserReadCommandRepositoryImpl;
import harmony.communityservice.community.command.repository.jpa.JpaBoardCommandRepository;
import harmony.communityservice.community.command.repository.jpa.JpaCategoryCommandRepository;
import harmony.communityservice.community.command.repository.jpa.JpaChannelCommandRepository;
import harmony.communityservice.community.command.repository.jpa.JpaCommentCommandRepository;
import harmony.communityservice.community.command.repository.jpa.JpaEmojiCommandRepository;
import harmony.communityservice.community.command.repository.jpa.JpaGuildCommandRepository;
import harmony.communityservice.community.command.repository.jpa.JpaGuildReadCommandRepository;
import harmony.communityservice.community.command.repository.jpa.JpaImageCommandRepository;
import harmony.communityservice.community.command.repository.jpa.JpaRoomCommandRepository;
import harmony.communityservice.community.command.repository.jpa.JpaUserCommandRepository;
import harmony.communityservice.community.command.repository.jpa.JpaUserReadCommandRepository;
import harmony.communityservice.community.command.service.BoardCommandService;
import harmony.communityservice.community.command.service.CategoryCommandService;
import harmony.communityservice.community.command.service.ChannelCommandService;
import harmony.communityservice.community.command.service.CommentCommandService;
import harmony.communityservice.community.command.service.EmojiCommandService;
import harmony.communityservice.community.command.service.GuildCommandService;
import harmony.communityservice.community.command.service.GuildReadCommandService;
import harmony.communityservice.community.command.service.ImageCommandService;
import harmony.communityservice.community.command.service.RoomCommandService;
import harmony.communityservice.community.command.service.UserCommandService;
import harmony.communityservice.community.command.service.UserReadCommandService;
import harmony.communityservice.community.command.service.impl.BoardCommandServiceImpl;
import harmony.communityservice.community.command.service.impl.CategoryCommandServiceImpl;
import harmony.communityservice.community.command.service.impl.ChannelCommandServiceImpl;
import harmony.communityservice.community.command.service.impl.CommentCommandServiceImpl;
import harmony.communityservice.community.command.service.impl.EmojiCommandServiceImpl;
import harmony.communityservice.community.command.service.impl.GuildCommandServiceImpl;
import harmony.communityservice.community.command.service.impl.GuildReadCommandServiceImpl;
import harmony.communityservice.community.command.service.impl.ImageCommandServiceImpl;
import harmony.communityservice.community.command.service.impl.RoomCommandServiceImpl;
import harmony.communityservice.community.command.service.impl.UserCommandServiceImpl;
import harmony.communityservice.community.command.service.impl.UserReadCommandServiceImpl;
import harmony.communityservice.community.query.service.BoardQueryService;
import harmony.communityservice.community.query.service.CategoryQueryService;
import harmony.communityservice.community.query.service.CommentQueryService;
import harmony.communityservice.community.query.service.EmojiQueryService;
import harmony.communityservice.community.query.service.GuildQueryService;
import harmony.communityservice.community.query.service.UserQueryService;
import harmony.communityservice.community.query.service.UserReadQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class AppCommandConfig {

    private final JpaUserCommandRepository jpaUserCommandRepository;
    private final JpaUserReadCommandRepository jpaUserReadCommandRepository;
    private final JpaGuildReadCommandRepository jpaGuildReadCommandRepository;
    private final JpaGuildCommandRepository jpaGuildCommandRepository;
    private final JpaCategoryCommandRepository categoryCommandRepository;
    private final JpaChannelCommandRepository jpaChannelCommandRepository;
    private final JpaImageCommandRepository jpaImageCommandRepository;
    private final JpaBoardCommandRepository jpaBoardCommandRepository;
    private final JpaCommentCommandRepository jpaCommentCommandRepository;
    private final JpaEmojiCommandRepository jpaEmojiCommandRepository;
    private final JpaRoomCommandRepository jpaRoomCommandRepository;
    private final UserQueryService userQueryService;
    private final GuildQueryService guildQueryService;
    private final UserReadQueryService userReadQueryService;
    private final ContentService contentService;
    private final CategoryQueryService categoryQueryService;
    private final BoardQueryService boardQueryService;
    private final CommentQueryService commentQueryService;
    private final EmojiQueryService emojiQueryService;
    private final ProducerService producerService;

    @Bean
    public UserCommandRepository userCommandRepository() {
        return new UserCommandRepositoryImpl(jpaUserCommandRepository);
    }

    @Bean
    public UserCommandService userCommandService() {
        return new UserCommandServiceImpl(userCommandRepository(), userQueryService, userReadQueryService);
    }

    @Bean
    public UserReadCommandRepository userReadCommandRepository() {
        return new UserReadCommandRepositoryImpl(jpaUserReadCommandRepository);
    }

    @Bean
    public UserReadCommandService userReadCommandService() {
        return new UserReadCommandServiceImpl(userReadCommandRepository());
    }

    @Bean
    public GuildReadCommandRepository guildReadCommandRepository() {
        return new GuildReadCommandRepositoryImpl(jpaGuildReadCommandRepository);
    }

    @Bean
    public GuildReadCommandService guildReadCommandService() {
        return new GuildReadCommandServiceImpl(guildReadCommandRepository(), producerService);
    }

    @Bean
    public GuildCommandRepository guildCommandRepository() {
        return new GuildCommandRepositoryImpl(jpaGuildCommandRepository);
    }

    @Bean
    public GuildCommandService guildCommandService() {
        return new GuildCommandServiceImpl(guildCommandRepository(), guildReadCommandService(), userQueryService,
                userReadCommandService(), guildQueryService, userReadQueryService,
                contentService, channelCommandService());
    }

    @Bean
    public CategoryCommandRepository categoryCommandRepository() {
        return new CategoryCommandRepositoryImpl(categoryCommandRepository);
    }

    @Bean
    public CategoryCommandService categoryCommandService() {
        return new CategoryCommandServiceImpl(userReadQueryService, categoryCommandRepository(),
                categoryQueryService);
    }


    @Bean
    public ChannelCommandRepository channelCommandRepository() {
        return new ChannelCommandRepositoryImpl(jpaChannelCommandRepository);
    }


    @Bean
    public ChannelCommandService channelCommandService() {
        return new ChannelCommandServiceImpl(channelCommandRepository(), userReadQueryService);
    }

    @Bean
    public ImageCommandRepository imageCommandRepository() {
        return new ImageCommandRepositoryImpl(jpaImageCommandRepository);
    }

    @Bean
    public ImageCommandService imageCommandService() {
        return new ImageCommandServiceImpl(imageCommandRepository());
    }

    @Bean
    public BoardCommandRepository boardCommandRepository() {
        return new BoardCommandRepositoryImpl(jpaBoardCommandRepository);
    }

    @Bean
    public BoardCommandService boardCommandService() {
        return new BoardCommandServiceImpl(contentService, userReadQueryService,
                imageCommandService(), boardCommandRepository(), boardQueryService);
    }

    @Bean
    public CommentCommandRepository commentCommandRepository() {
        return new CommentCommandRepositoryImpl(jpaCommentCommandRepository);
    }

    @Bean
    public CommentCommandService commentCommandService() {
        return new CommentCommandServiceImpl(commentCommandRepository(), commentQueryService);
    }

    @Bean
    public EmojiCommandRepository emojiCommandRepository() {
        return new EmojiCommandRepositoryImpl(jpaEmojiCommandRepository);
    }


    @Bean
    public EmojiCommandService emojiCommandService() {
        return new EmojiCommandServiceImpl(boardQueryService, emojiCommandRepository(), emojiQueryService);
    }

    @Bean
    public RoomCommandRepository roomCommandRepository() {
        return new RoomCommandRepositoryImpl(jpaRoomCommandRepository);
    }

    @Bean
    public RoomCommandService roomCommandService() {
        return new RoomCommandServiceImpl(roomCommandRepository());
    }
}

package harmony.communityservice.guild.config;

import harmony.communityservice.common.service.ContentService;
import harmony.communityservice.common.service.ProducerService;
import harmony.communityservice.guild.category.repository.command.CategoryCommandRepository;
import harmony.communityservice.guild.category.repository.command.impl.CategoryCommandRepositoryImpl;
import harmony.communityservice.guild.category.repository.command.jpa.JpaCategoryCommandRepository;
import harmony.communityservice.guild.category.service.command.CategoryCommandService;
import harmony.communityservice.guild.category.service.command.impl.CategoryCommandServiceImpl;
import harmony.communityservice.guild.category.service.query.CategoryQueryService;
import harmony.communityservice.guild.channel.repository.command.ChannelCommandRepository;
import harmony.communityservice.guild.channel.repository.command.impl.ChannelCommandRepositoryImpl;
import harmony.communityservice.guild.channel.repository.command.jpa.JpaChannelCommandRepository;
import harmony.communityservice.guild.channel.service.command.ChannelCommandService;
import harmony.communityservice.guild.channel.service.command.impl.ChannelCommandServiceImpl;
import harmony.communityservice.guild.guild.repository.command.GuildCommandRepository;
import harmony.communityservice.guild.guild.repository.command.GuildReadCommandRepository;
import harmony.communityservice.guild.guild.repository.command.impl.GuildCommandRepositoryImpl;
import harmony.communityservice.guild.guild.repository.command.impl.GuildReadCommandRepositoryImpl;
import harmony.communityservice.guild.guild.repository.command.jpa.JpaGuildCommandRepository;
import harmony.communityservice.guild.guild.repository.command.jpa.JpaGuildReadCommandRepository;
import harmony.communityservice.guild.guild.service.command.GuildCommandService;
import harmony.communityservice.guild.guild.service.command.GuildReadCommandService;
import harmony.communityservice.guild.guild.service.command.impl.GuildCommandServiceImpl;
import harmony.communityservice.guild.guild.service.command.impl.GuildReadCommandServiceImpl;
import harmony.communityservice.guild.guild.service.query.GuildQueryService;
import harmony.communityservice.user.service.command.UserReadCommandService;
import harmony.communityservice.user.service.query.UserReadQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class GuildCommandConfig {

    private final JpaCategoryCommandRepository jpaCategoryCommandRepository;
    private final CategoryQueryService categoryQueryService;
    private final JpaChannelCommandRepository jpaChannelCommandRepository;
    private final JpaGuildCommandRepository jpaGuildCommandRepository;
    private final JpaGuildReadCommandRepository jpaGuildReadCommandRepository;
    private final ProducerService producerService;
    private final UserReadCommandService userReadCommandService;
    private final GuildQueryService guildQueryService;
    private final UserReadQueryService userReadQueryService;
    private final ContentService contentService;

    @Bean
    public CategoryCommandRepository categoryCommandRepository() {
        return new CategoryCommandRepositoryImpl(jpaCategoryCommandRepository);
    }

    @Bean
    public CategoryCommandService categoryCommandService() {
        return new CategoryCommandServiceImpl(categoryCommandRepository(), categoryQueryService);
    }

    @Bean
    public ChannelCommandRepository channelCommandRepository() {
        return new ChannelCommandRepositoryImpl(jpaChannelCommandRepository);
    }

    @Bean
    public ChannelCommandService channelCommandService() {
        return new ChannelCommandServiceImpl(channelCommandRepository());
    }

    @Bean
    public GuildCommandRepository guildCommandRepository() {
        return new GuildCommandRepositoryImpl(jpaGuildCommandRepository);
    }

    @Bean
    public GuildCommandService guildCommandService() {
        return new GuildCommandServiceImpl(guildCommandRepository(), guildReadCommandService(), userReadCommandService,
                guildQueryService, userReadQueryService, contentService, channelCommandService());
    }

    @Bean
    public GuildReadCommandRepository guildReadCommandRepository() {
        return new GuildReadCommandRepositoryImpl(jpaGuildReadCommandRepository);
    }

    @Bean
    public GuildReadCommandService guildReadCommandService() {
        return new GuildReadCommandServiceImpl(guildReadCommandRepository(), producerService);
    }
}

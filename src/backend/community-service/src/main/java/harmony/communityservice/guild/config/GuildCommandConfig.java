package harmony.communityservice.guild.config;

import harmony.communityservice.common.service.ContentService;
import harmony.communityservice.common.service.ProducerService;
import harmony.communityservice.guild.category.service.command.CategoryCommandService;
import harmony.communityservice.guild.category.service.command.impl.CategoryCommandServiceImpl;
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
import harmony.communityservice.user.service.command.UserReadCommandService;
import harmony.communityservice.user.service.query.UserReadQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class GuildCommandConfig {
    private final JpaGuildCommandRepository jpaGuildCommandRepository;
    private final JpaGuildReadCommandRepository jpaGuildReadCommandRepository;
    private final ProducerService producerService;
    private final UserReadCommandService userReadCommandService;
    private final UserReadQueryService userReadQueryService;
    private final ContentService contentService;


    @Bean
    public CategoryCommandService categoryCommandService() {
        return new CategoryCommandServiceImpl(guildCommandRepository());
    }


    @Bean
    public ChannelCommandService channelCommandService() {
        return new ChannelCommandServiceImpl(guildCommandRepository());
    }

    @Bean
    public GuildCommandRepository guildCommandRepository() {
        return new GuildCommandRepositoryImpl(jpaGuildCommandRepository);
    }

    @Bean
    public GuildCommandService guildCommandService() {
        return new GuildCommandServiceImpl(guildCommandRepository(), guildReadCommandService(), userReadCommandService,
                userReadQueryService, contentService);
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

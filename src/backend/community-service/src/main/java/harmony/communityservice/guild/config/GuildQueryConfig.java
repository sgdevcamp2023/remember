package harmony.communityservice.guild.config;

import harmony.communityservice.common.client.UserStatusClient;
import harmony.communityservice.guild.category.service.query.CategoryQueryService;
import harmony.communityservice.guild.category.service.query.impl.CategoryQueryServiceImpl;
import harmony.communityservice.guild.channel.repository.query.ChannelQueryRepository;
import harmony.communityservice.guild.channel.repository.query.impl.ChannelQueryRepositoryImpl;
import harmony.communityservice.guild.channel.repository.query.jpa.JpaChannelQueryRepository;
import harmony.communityservice.guild.channel.service.query.ChannelQueryService;
import harmony.communityservice.guild.channel.service.query.impl.ChannelQueryServiceImpl;
import harmony.communityservice.guild.guild.repository.query.GuildQueryRepository;
import harmony.communityservice.guild.guild.repository.query.GuildReadQueryRepository;
import harmony.communityservice.guild.guild.repository.query.impl.GuildQueryRepositoryImpl;
import harmony.communityservice.guild.guild.repository.query.impl.GuildReadQueryRepositoryImpl;
import harmony.communityservice.guild.guild.repository.query.jpa.JpaGuildQueryRepository;
import harmony.communityservice.guild.guild.repository.query.jpa.JpaGuildReadQueryRepository;
import harmony.communityservice.guild.guild.service.query.GuildQueryService;
import harmony.communityservice.guild.guild.service.query.GuildReadQueryService;
import harmony.communityservice.guild.guild.service.query.impl.GuildQueryServiceImpl;
import harmony.communityservice.guild.guild.service.query.impl.GuildReadQueryServiceImpl;
import harmony.communityservice.user.service.query.UserReadQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class GuildQueryConfig {
    private final JpaChannelQueryRepository jpaChannelQueryRepository;
    private final JpaGuildQueryRepository jpaGuildQueryRepository;
    private final JpaGuildReadQueryRepository jpaGuildReadQueryRepository;
    private final UserReadQueryService userReadQueryService;
    private final UserStatusClient userStatusClient;


    @Bean
    public CategoryQueryService categoryQueryService() {
        return new CategoryQueryServiceImpl(guildQueryService());
    }

    @Bean
    public ChannelQueryRepository channelQueryRepository() {
        return new ChannelQueryRepositoryImpl(jpaChannelQueryRepository);
    }

    @Bean
    public ChannelQueryService channelQueryService() {
        return new ChannelQueryServiceImpl(channelQueryRepository());
    }

    @Bean
    public GuildQueryRepository guildQueryRepository() {
        return new GuildQueryRepositoryImpl(jpaGuildQueryRepository);
    }

    @Bean
    public GuildQueryService guildQueryService() {
        return new GuildQueryServiceImpl(guildQueryRepository(), userReadQueryService, userStatusClient);
    }

    @Bean
    public GuildReadQueryRepository guildReadQueryRepository() {
        return new GuildReadQueryRepositoryImpl(jpaGuildReadQueryRepository);
    }

    @Bean
    public GuildReadQueryService guildReadQueryService() {
        return new GuildReadQueryServiceImpl(guildReadQueryRepository());
    }
}

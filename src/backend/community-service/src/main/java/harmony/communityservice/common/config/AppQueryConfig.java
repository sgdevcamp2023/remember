package harmony.communityservice.common.config;

import harmony.communityservice.community.query.repository.GuildQueryRepository;
import harmony.communityservice.community.query.repository.UserQueryRepository;
import harmony.communityservice.community.query.repository.UserReadQueryRepository;
import harmony.communityservice.community.query.repository.impl.GuildQueryRepositoryImpl;
import harmony.communityservice.community.query.repository.impl.UserQueryRepositoryImpl;
import harmony.communityservice.community.query.repository.impl.UserReadQueryRepositoryImpl;
import harmony.communityservice.community.query.repository.jpa.JpaGuildQueryRepository;
import harmony.communityservice.community.query.repository.jpa.JpaUserQueryRepository;
import harmony.communityservice.community.query.repository.jpa.JpaUserReadQueryRepository;
import harmony.communityservice.community.query.service.GuildQueryService;
import harmony.communityservice.community.query.service.UserQueryService;
import harmony.communityservice.community.query.service.UserReadQueryService;
import harmony.communityservice.community.query.service.impl.GuildQueryServiceImpl;
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
        return new UserReadQueryServiceImpl(userReadQueryRepository());
    }

    @Bean
    public GuildQueryRepository guildQueryRepository() {
        return new GuildQueryRepositoryImpl(jpaGuildQueryRepository);
    }

    @Bean
    public GuildQueryService guildQueryService() {
        return new GuildQueryServiceImpl(guildQueryRepository(), userReadQueryService());
    }
}

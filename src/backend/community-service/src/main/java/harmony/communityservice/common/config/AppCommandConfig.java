package harmony.communityservice.common.config;

import harmony.communityservice.community.command.repository.GuildCommandRepository;
import harmony.communityservice.community.command.repository.GuildReadCommandRepository;
import harmony.communityservice.community.command.repository.GuildUserCommandRepository;
import harmony.communityservice.community.command.repository.UserCommandRepository;
import harmony.communityservice.community.command.repository.UserReadCommandRepository;
import harmony.communityservice.community.command.repository.impl.GuildCommandRepositoryImpl;
import harmony.communityservice.community.command.repository.impl.GuildReadCommandRepositoryImpl;
import harmony.communityservice.community.command.repository.impl.GuildUserCommandRepositoryImpl;
import harmony.communityservice.community.command.repository.impl.UserCommandRepositoryImpl;
import harmony.communityservice.community.command.repository.impl.UserReadCommandRepositoryImpl;
import harmony.communityservice.community.command.repository.jpa.JpaGuildCommandRepository;
import harmony.communityservice.community.command.repository.jpa.JpaGuildReadCommandRepository;
import harmony.communityservice.community.command.repository.jpa.JpaGuildUserCommandRepository;
import harmony.communityservice.community.command.repository.jpa.JpaUserCommandRepository;
import harmony.communityservice.community.command.repository.jpa.JpaUserReadCommandRepository;
import harmony.communityservice.community.command.service.GuildCommandService;
import harmony.communityservice.community.command.service.GuildReadCommandService;
import harmony.communityservice.community.command.service.GuildUserCommandService;
import harmony.communityservice.community.command.service.UserCommandService;
import harmony.communityservice.community.command.service.UserReadCommandService;
import harmony.communityservice.community.command.service.impl.GuildCommandServiceImpl;
import harmony.communityservice.community.command.service.impl.GuildReadCommandServiceImpl;
import harmony.communityservice.community.command.service.impl.GuildUserCommandServiceImpl;
import harmony.communityservice.community.command.service.impl.UserCommandServiceImpl;
import harmony.communityservice.community.command.service.impl.UserReadCommandServiceImpl;
import harmony.communityservice.community.query.service.UserQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class AppCommandConfig {

    private final JpaUserCommandRepository jpaUserCommandRepository;
    private final JpaUserReadCommandRepository jpaUserReadCommandRepository;
    private final JpaGuildUserCommandRepository jpaGuildUserCommandRepository;
    private final JpaGuildReadCommandRepository jpaGuildReadCommandRepository;
    private final JpaGuildCommandRepository jpaGuildCommandRepository;
    private final UserQueryService userQueryService;

    @Bean
    public UserCommandRepository userCommandRepository() {
        return new UserCommandRepositoryImpl(jpaUserCommandRepository);
    }

    @Bean
    public UserCommandService userCommandService() {
        return new UserCommandServiceImpl(userCommandRepository());
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
    public GuildUserCommandRepository guildUserCommandRepository() {
        return new GuildUserCommandRepositoryImpl(jpaGuildUserCommandRepository);
    }

    @Bean
    public GuildUserCommandService guildUserCommandService() {
        return new GuildUserCommandServiceImpl(guildUserCommandRepository());
    }

    @Bean
    public GuildReadCommandRepository guildReadCommandRepository() {
        return new GuildReadCommandRepositoryImpl(jpaGuildReadCommandRepository);
    }

    @Bean
    public GuildReadCommandService guildReadCommandService() {
        return new GuildReadCommandServiceImpl(guildReadCommandRepository());
    }

    @Bean
    public GuildCommandRepository guildCommandRepository() {
        return new GuildCommandRepositoryImpl(jpaGuildCommandRepository);
    }

    @Bean
    public GuildCommandService guildCommandService() {
        return new GuildCommandServiceImpl(guildCommandRepository(), guildReadCommandService(), userQueryService,
                guildUserCommandService(), userReadCommandService());
    }
}
package harmony.communityservice.user.config;

import harmony.communityservice.user.repository.command.UserCommandRepository;
import harmony.communityservice.user.repository.command.UserReadCommandRepository;
import harmony.communityservice.user.repository.command.impl.UserCommandRepositoryImpl;
import harmony.communityservice.user.repository.command.impl.UserReadCommandRepositoryImpl;
import harmony.communityservice.user.repository.command.jpa.JpaUserCommandRepository;
import harmony.communityservice.user.repository.command.jpa.JpaUserReadCommandRepository;
import harmony.communityservice.user.service.command.UserCommandService;
import harmony.communityservice.user.service.command.UserReadCommandService;
import harmony.communityservice.user.service.command.impl.UserCommandServiceImpl;
import harmony.communityservice.user.service.command.impl.UserReadCommandServiceImpl;
import harmony.communityservice.user.service.query.UserQueryService;
import harmony.communityservice.user.service.query.UserReadQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class UserCommandConfig {

    private final JpaUserCommandRepository jpaUserCommandRepository;
    private final JpaUserReadCommandRepository jpaUserReadCommandRepository;
    private final UserQueryService userQueryService;
    private final UserReadQueryService userReadQueryService;

    @Bean
    public UserCommandRepository userCommandRepository() {
        return new UserCommandRepositoryImpl(jpaUserCommandRepository);
    }


    @Bean
    public UserReadCommandRepository userReadCommandRepository() {
        return new UserReadCommandRepositoryImpl(jpaUserReadCommandRepository);
    }

    @Bean
    public UserCommandService userCommandService() {
        return new UserCommandServiceImpl(userCommandRepository(), userQueryService, userReadQueryService);
    }

    @Bean
    public UserReadCommandService userReadCommandService() {
        return new UserReadCommandServiceImpl(userQueryService, userReadCommandRepository());
    }
}

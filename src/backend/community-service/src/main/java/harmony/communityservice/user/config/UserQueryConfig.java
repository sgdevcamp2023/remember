package harmony.communityservice.user.config;

import harmony.communityservice.user.repository.query.UserQueryRepository;
import harmony.communityservice.user.repository.query.UserReadQueryRepository;
import harmony.communityservice.user.repository.query.impl.UserQueryRepositoryImpl;
import harmony.communityservice.user.repository.query.impl.UserReadQueryRepositoryImpl;
import harmony.communityservice.user.repository.query.jpa.JpaUserQueryRepository;
import harmony.communityservice.user.repository.query.jpa.JpaUserReadQueryRepository;
import harmony.communityservice.user.service.query.UserQueryService;
import harmony.communityservice.user.service.query.UserReadQueryService;
import harmony.communityservice.user.service.query.impl.UserQueryServiceImpl;
import harmony.communityservice.user.service.query.impl.UserReadQueryServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class UserQueryConfig {

    private final JpaUserQueryRepository jpaUserQueryRepository;
    private final JpaUserReadQueryRepository jpaUserReadQueryRepository;

    @Bean
    public UserQueryRepository userQueryRepository() {
        return new UserQueryRepositoryImpl(jpaUserQueryRepository);
    }

    @Bean
    public UserReadQueryRepository userReadQueryRepository() {
        return new UserReadQueryRepositoryImpl(jpaUserReadQueryRepository);
    }

    @Bean
    public UserQueryService userQueryService() {
        return new UserQueryServiceImpl(userQueryRepository());
    }

    @Bean
    public UserReadQueryService userReadQueryService() {
        return new UserReadQueryServiceImpl(userReadQueryRepository());
    }
}

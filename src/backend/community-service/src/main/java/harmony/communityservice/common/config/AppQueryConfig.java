package harmony.communityservice.common.config;

import harmony.communityservice.community.query.repository.UserQueryRepository;
import harmony.communityservice.community.query.repository.impl.UserQueryRepositoryImpl;
import harmony.communityservice.community.query.repository.jpa.JpaUserQueryRepository;
import harmony.communityservice.community.query.service.UserQueryService;
import harmony.communityservice.community.query.service.impl.UserQueryServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class AppQueryConfig {

    private final JpaUserQueryRepository jpaUserQueryRepository;

    @Bean
    public UserQueryRepository userQueryRepository() {
        return new UserQueryRepositoryImpl(jpaUserQueryRepository);
    }

    @Bean
    public UserQueryService userQueryService() {
        return new UserQueryServiceImpl(userQueryRepository());
    }
}

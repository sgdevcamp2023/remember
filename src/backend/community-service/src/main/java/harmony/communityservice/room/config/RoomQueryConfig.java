package harmony.communityservice.room.config;

import harmony.communityservice.common.client.UserStatusClient;
import harmony.communityservice.room.repository.query.RoomQueryRepository;
import harmony.communityservice.room.repository.query.impl.RoomQueryRepositoryImpl;
import harmony.communityservice.room.repository.query.jpa.JpaRoomQueryRepository;
import harmony.communityservice.room.service.query.RoomQueryService;
import harmony.communityservice.room.service.query.impl.RoomQueryServiceImpl;
import harmony.communityservice.user.service.query.UserQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RoomQueryConfig {

    private final JpaRoomQueryRepository jpaRoomQueryRepository;
    private final UserQueryService userQueryService;
    private final UserStatusClient userStatusClient;
    @Bean
    public RoomQueryRepository roomQueryRepository() {
        return new RoomQueryRepositoryImpl(jpaRoomQueryRepository);
    }

    @Bean
    public RoomQueryService roomQueryService() {
        return new RoomQueryServiceImpl(userQueryService, roomQueryRepository(), userStatusClient);
    }
}

package harmony.communityservice.room.config;

import harmony.communityservice.room.repository.command.RoomCommandRepository;
import harmony.communityservice.room.repository.command.impl.RoomCommandRepositoryImpl;
import harmony.communityservice.room.repository.command.jpa.JpaRoomCommandRepository;
import harmony.communityservice.room.service.command.RoomCommandService;
import harmony.communityservice.room.service.command.impl.RoomCommandServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RoomCommandConfig {

    private final JpaRoomCommandRepository jpaRoomCommandRepository;

    @Bean
    public RoomCommandRepository roomCommandRepository() {
        return new RoomCommandRepositoryImpl(jpaRoomCommandRepository);
    }

    @Bean
    public RoomCommandService roomCommandService() {
        return new RoomCommandServiceImpl(roomCommandRepository());
    }

}

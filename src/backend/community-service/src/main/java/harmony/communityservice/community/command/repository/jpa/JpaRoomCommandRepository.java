package harmony.communityservice.community.command.repository.jpa;

import harmony.communityservice.community.domain.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaRoomCommandRepository extends JpaRepository<Room, Long> {
    void deleteRoomByRoomId(long roomId);
}

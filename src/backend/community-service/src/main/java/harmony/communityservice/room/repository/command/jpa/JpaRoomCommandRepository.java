package harmony.communityservice.room.repository.command.jpa;

import harmony.communityservice.room.domain.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaRoomCommandRepository extends JpaRepository<Room, Long> {

    void deleteRoomByUserIdsContainingAndUserIdsContaining(Long first, Long Second);
}

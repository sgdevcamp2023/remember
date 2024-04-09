package harmony.communityservice.room.repository.query.jpa;

import harmony.communityservice.room.domain.Room;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaRoomQueryRepository extends JpaRepository<Room, Long> {

    List<Room> findRoomsByUserIdsContains(Long userId);
}

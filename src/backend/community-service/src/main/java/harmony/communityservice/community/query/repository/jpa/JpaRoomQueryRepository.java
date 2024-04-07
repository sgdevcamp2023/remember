package harmony.communityservice.community.query.repository.jpa;

import harmony.communityservice.community.domain.Room;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaRoomQueryRepository extends JpaRepository<Room, Long> {

    List<Room> findRoomsByUserIdsContains(Long userId);
}

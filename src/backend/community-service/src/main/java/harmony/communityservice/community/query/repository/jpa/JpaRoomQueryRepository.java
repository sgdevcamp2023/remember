package harmony.communityservice.community.query.repository.jpa;

import harmony.communityservice.community.domain.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaRoomQueryRepository extends JpaRepository<Room, Long> {
}

package harmony.communityservice.community.command.repository.jpa;

import harmony.communityservice.community.domain.Room;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaRoomCommandRepository extends JpaRepository<Room, Long> {

    void deleteRoomByUserIdsContainingAndUserIdsContaining(Long first, Long Second);
}

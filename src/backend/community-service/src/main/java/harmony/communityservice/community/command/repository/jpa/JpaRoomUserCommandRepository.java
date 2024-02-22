package harmony.communityservice.community.command.repository.jpa;

import harmony.communityservice.community.domain.RoomUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaRoomUserCommandRepository extends JpaRepository<RoomUser, Long> {
}

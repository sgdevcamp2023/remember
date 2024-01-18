package harmony.communityservice.community.command.service;

import harmony.communityservice.community.domain.Room;
import harmony.communityservice.community.domain.User;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface RoomUserCommandService {
    void save(Room room, User user);
}

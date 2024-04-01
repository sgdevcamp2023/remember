package harmony.communityservice.community.command.service;

import harmony.communityservice.community.domain.Room;
import harmony.communityservice.community.domain.RoomUser;
import harmony.communityservice.community.domain.User;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface RoomUserCommandService {

    void register(Room room, User user);

    void delete(RoomUser roomUser);
}

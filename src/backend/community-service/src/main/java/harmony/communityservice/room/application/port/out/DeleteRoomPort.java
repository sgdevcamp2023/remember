package harmony.communityservice.room.application.port.out;

import harmony.communityservice.user.domain.User.UserId;

public interface DeleteRoomPort {

    void delete(UserId firstUserId, UserId secondUserId);
}

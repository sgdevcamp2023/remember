package harmony.communityservice.room.domain;

import harmony.communityservice.user.domain.User.UserId;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class RoomUser {

    private RoomUserId roomUserId;

    private final UserId userId;

    private RoomUser(UserId userId) {
        this.userId = userId;
    }

    public static RoomUser make(UserId userId){
        return new RoomUser(userId);
    }



    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class RoomUserId{
        private final Long id;

        public static RoomUserId make(Long id) {
            return new RoomUserId(id);
        }
    }
}

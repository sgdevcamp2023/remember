package harmony.communityservice.room.domain;

import harmony.communityservice.domain.Domain;
import harmony.communityservice.domain.ValueObject;
import harmony.communityservice.room.domain.RoomUser.RoomUserId;
import harmony.communityservice.user.domain.User.UserId;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class RoomUser extends Domain<RoomUser, RoomUserId> {

    private final UserId userId;
    private RoomUserId roomUserId;

    private RoomUser(UserId userId) {
        this.userId = userId;
    }

    private RoomUser(RoomUserId roomUserId, UserId userId) {
        this.roomUserId = roomUserId;
        this.userId = userId;
    }

    public static RoomUser make(UserId userId) {
        return new RoomUser(userId);
    }

    public static RoomUser make(RoomUserId roomUserId, UserId userId) {
        return new RoomUser(roomUserId, userId);
    }

    @Override
    public RoomUserId getId() {
        return roomUserId;
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class RoomUserId extends ValueObject<RoomUserId> {
        private final Long id;

        public static RoomUserId make(Long id) {
            return new RoomUserId(id);
        }

        @Override
        protected Object[] getEqualityFields() {
            return new Object[]{id};
        }
    }
}

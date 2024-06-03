package harmony.communityservice.room.domain;

import harmony.communityservice.common.exception.NotFoundDataException;
import harmony.communityservice.common.exception.WrongThresholdRangeException;
import harmony.communityservice.domain.Domain;
import harmony.communityservice.domain.Threshold;
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
        verifyUserId(userId);
        this.userId = userId;
    }

    private RoomUser(RoomUserId roomUserId, UserId userId) {
        verifyRoomUserId(roomUserId);
        this.roomUserId = roomUserId;
        verifyUserId(userId);
        this.userId = userId;
    }

    public static RoomUser make(UserId userId) {
        return new RoomUser(userId);
    }

    public static RoomUser make(RoomUserId roomUserId, UserId userId) {
        return new RoomUser(roomUserId, userId);
    }

    private void verifyUserId(UserId userId) {
        if (userId.getId() < Threshold.MIN.getValue()) {
            throw new WrongThresholdRangeException("userId 범위가 1 미만입니다");
        }
    }

    private void verifyRoomUserId(RoomUserId roomUserId) {
        if (roomUserId != null && roomUserId.getId() < Threshold.MIN.getValue()) {
            throw new WrongThresholdRangeException("roomUserId의 범위가 1 미만입니다");
        }
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
    }
}

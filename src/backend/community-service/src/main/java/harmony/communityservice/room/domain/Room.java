package harmony.communityservice.room.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import harmony.communityservice.common.exception.NotFoundDataException;
import harmony.communityservice.common.exception.WrongThresholdRangeException;
import harmony.communityservice.domain.Domain;
import harmony.communityservice.domain.Threshold;
import harmony.communityservice.domain.ValueObject;
import harmony.communityservice.room.domain.Room.RoomId;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@JsonInclude(Include.NON_NULL)
public class Room extends Domain<Room, RoomId> {

    private final RoomId roomId;

    private final List<RoomUser> roomUsers;

    private final ProfileInfo profileInfo;


    @Builder
    public Room(String profile, String name, RoomId roomId, List<RoomUser> roomUsers) {
        verifyProfileInfo(profile, name);
        this.profileInfo = ProfileInfo.make(name, profile);
        verifyRoomId(roomId);
        this.roomId = roomId;
        this.roomUsers = roomUsers;
    }

    private void verifyRoomId(RoomId roomId) {
        if (roomId != null && roomId.getId() < Threshold.MIN.getValue()) {
            throw new WrongThresholdRangeException("roomId가 1 미만입니다");
        }
    }

    private void verifyProfileInfo(String profile, String name) {
        if (profile == null || name == null) {
            throw new NotFoundDataException("데이터가 없습니다");
        }
    }


    @Override
    public RoomId getId() {
        return roomId;
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class RoomId extends ValueObject<RoomId> {

        private final Long id;

        public static RoomId make(Long id) {
            return new RoomId(id);
        }

        @Override
        protected Object[] getEqualityFields() {
            return new Object[]{id};
        }
    }
}

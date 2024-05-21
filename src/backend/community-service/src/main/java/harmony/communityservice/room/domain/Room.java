package harmony.communityservice.room.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import harmony.communityservice.common.exception.NotFoundDataException;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@JsonInclude(Include.NON_NULL)
public class Room {

    private final RoomId roomId;

    private final List<RoomUser> roomUsers;

    private final ProfileInfo profileInfo;


    @Builder
    public Room(String profile, String name, RoomId roomId, List<RoomUser> roomUsers) {
        verifyProfileInfo(profile, name);
        this.profileInfo = ProfileInfo.make(name, profile);
        this.roomId = roomId;
        this.roomUsers = roomUsers;
    }

    private void verifyProfileInfo(String profile, String name) {
        if (profile == null || name == null) {
            throw new NotFoundDataException("데이터가 없습니다");
        }
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class RoomId {

        private final Long id;

        public static RoomId make(Long id) {
            return new RoomId(id);
        }
    }
}

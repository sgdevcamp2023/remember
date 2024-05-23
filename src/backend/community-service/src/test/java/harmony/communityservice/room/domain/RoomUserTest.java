package harmony.communityservice.room.domain;

import static org.junit.jupiter.api.Assertions.*;

import harmony.communityservice.room.domain.RoomUser.RoomUserId;
import harmony.communityservice.user.domain.User.UserId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RoomUserTest {


    @Test
    @DisplayName("식별자가 같으면 같은 객체로 인식")
    void same_roomUser() {
        RoomUser firstRoomUser = RoomUser.make(RoomUserId.make(1L), UserId.make(1L));
        RoomUser secondRoomUser = RoomUser.make(RoomUserId.make(1L), UserId.make(2L));

        boolean equals = firstRoomUser.equals(secondRoomUser);

        assertSame(equals,true);
    }

    @Test
    @DisplayName("식별자가 다르면 다른 객체로 인식")
    void different_roomUser() {
        RoomUser firstRoomUser = RoomUser.make(RoomUserId.make(1L), UserId.make(1L));
        RoomUser secondRoomUser = RoomUser.make(RoomUserId.make(2L), UserId.make(1L));

        boolean equals = firstRoomUser.equals(secondRoomUser);

        assertSame(equals,false);
    }
}
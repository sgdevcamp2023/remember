package harmony.communityservice.room.domain;

import static org.junit.jupiter.api.Assertions.*;

import harmony.communityservice.common.exception.WrongThresholdRangeException;
import harmony.communityservice.room.domain.RoomUser.RoomUserId;
import harmony.communityservice.user.domain.User.UserId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

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

    @Test
    @DisplayName("userId가 0이면 예외 테스트")
    void userId_equal_zero() {
        assertThrows(WrongThresholdRangeException.class, () -> RoomUser.make(RoomUserId.make(1L), UserId.make(0L)));
    }

    @Test
    @DisplayName("roomUserId가 0이면 예외 테스트")
    void room_userId_equal_zero() {
        assertThrows(WrongThresholdRangeException.class, () -> RoomUser.make(RoomUserId.make(0L), UserId.make(1L)));
    }


    @ParameterizedTest
    @DisplayName("userId 범위 테스트")
    @ValueSource(longs = {0L,-1L,-10L,-100L,-1000L})
    void user_id_range_threshold(Long userId) {
        assertThrows(WrongThresholdRangeException.class,
                ()->RoomUser.make(RoomUserId.make(1L), UserId.make(userId)));
    }

    @ParameterizedTest
    @DisplayName("roomUserId 범위 테스트")
    @ValueSource(longs = {0L,-1L,-10L,-100L,-1000L})
    void room_user_id_range_threshold(Long roomUserId) {
        assertThrows(WrongThresholdRangeException.class,
                ()->RoomUser.make(RoomUserId.make(roomUserId), UserId.make(1L)));
    }
}
package harmony.communityservice.room.domain;

import static org.junit.jupiter.api.Assertions.*;

import harmony.communityservice.common.exception.NotFoundDataException;
import harmony.communityservice.common.exception.WrongThresholdRangeException;
import harmony.communityservice.room.domain.Room.RoomId;
import harmony.communityservice.user.domain.User.UserId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class RoomTest {

    @Test
    @DisplayName("같은 식별자면 같은 객체로 인식 테스트")
    void same_room() {

        RoomUser first = RoomUser.make(UserId.make(1L));
        RoomUser second = RoomUser.make(UserId.make(2L));
        RoomUser third = RoomUser.make(UserId.make(3L));
        RoomUser fourth = RoomUser.make(UserId.make(4L));
        List<RoomUser> firstRoomUsers = new ArrayList<>(Arrays.asList(first, second));
        List<RoomUser> secondRoomUsers = new ArrayList<>(Arrays.asList(third, fourth));
        Room fisrtRoom = Room.builder()
                .name("first_room")
                .profile("http://cdn.com/test")
                .roomId(RoomId.make(1L))
                .roomUsers(firstRoomUsers)
                .build();

        Room secondRoom = Room.builder()
                .name("second_room")
                .profile("http://cdn.com/test2")
                .roomId(RoomId.make(1L))
                .roomUsers(secondRoomUsers)
                .build();

        boolean equals = fisrtRoom.equals(secondRoom);

        assertSame(equals, true);
    }

    @Test
    @DisplayName("다른 식별자면 다른 객체로 인식 테스트")
    void different_room() {

        RoomUser first = RoomUser.make(UserId.make(1L));
        RoomUser second = RoomUser.make(UserId.make(2L));
        RoomUser third = RoomUser.make(UserId.make(3L));
        RoomUser fourth = RoomUser.make(UserId.make(4L));
        List<RoomUser> firstRoomUsers = new ArrayList<>(Arrays.asList(first, second));
        List<RoomUser> secondRoomUsers = new ArrayList<>(Arrays.asList(third, fourth));
        Room fisrtRoom = Room.builder()
                .name("first_room")
                .profile("http://cdn.com/test")
                .roomId(RoomId.make(1L))
                .roomUsers(firstRoomUsers)
                .build();

        Room secondRoom = Room.builder()
                .name("first_room")
                .profile("http://cdn.com/test")
                .roomId(RoomId.make(2L))
                .roomUsers(secondRoomUsers)
                .build();

        boolean equals = fisrtRoom.equals(secondRoom);

        assertSame(equals, false);
    }

    @Test
    @DisplayName("프로필 정보가 없으면 NotFoundDataException throw 테스트")
    void not_have_profile() {

        RoomUser first = RoomUser.make(UserId.make(1L));
        RoomUser second = RoomUser.make(UserId.make(2L));
        List<RoomUser> firstRoomUsers = new ArrayList<>(Arrays.asList(first, second));
        assertThrows(NotFoundDataException.class,()->{
            Room.builder()
                    .name("first_room")
                    .roomId(RoomId.make(1L))
                    .roomUsers(firstRoomUsers)
                    .build();

        });
    }

    @Test
    @DisplayName("room name 정보가 없으면 NotFoundDataException throw 테스트")
    void not_have_room_name() {

        RoomUser first = RoomUser.make(UserId.make(1L));
        RoomUser second = RoomUser.make(UserId.make(2L));
        List<RoomUser> firstRoomUsers = new ArrayList<>(Arrays.asList(first, second));
        assertThrows(NotFoundDataException.class,()->{
            Room.builder()
                    .profile("http://cdn.com/test")
                    .roomId(RoomId.make(1L))
                    .roomUsers(firstRoomUsers)
                    .build();
        });
    }


    @ParameterizedTest
    @DisplayName("roomId 범위 테스트")
    @ValueSource(longs = {0L, -1L, -10L, -100L, -1000L})
    void room_id_range_threshold(long roomId) {

        RoomUser first = RoomUser.make(UserId.make(1L));
        RoomUser second = RoomUser.make(UserId.make(2L));
        List<RoomUser> firstRoomUsers = new ArrayList<>(Arrays.asList(first, second));
        assertThrows(WrongThresholdRangeException.class,()->{
            Room.builder()
                    .profile("http://cdn.com/test")
                    .roomId(RoomId.make(roomId))
                    .name("test")
                    .roomUsers(firstRoomUsers)
                    .build();
        });
    }
}
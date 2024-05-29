package harmony.communityservice.room.adapter.out.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;

import harmony.communityservice.common.exception.NotFoundDataException;
import harmony.communityservice.room.domain.Room;
import harmony.communityservice.room.domain.Room.RoomId;
import harmony.communityservice.user.adapter.out.persistence.UserIdJpaVO;
import harmony.communityservice.user.domain.User.UserId;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Rollback(value = false)
@SpringBootTest
@ActiveProfiles("test")
@EnableTransactionManagement
class RoomQueryPersistenceAdapterTest {

    @Autowired
    RoomQueryPersistenceAdapter roomQueryPersistenceAdapter;

    @Autowired
    RoomCommandPersistenceAdapter roomCommandPersistenceAdapter;

    @Autowired
    RoomQueryRepository roomQueryRepository;

    @Test
    @DisplayName("유저가 속한 룸 정보들 조회 테스트")
    @Sql("RoomQueryPersistenceAdapterTest.sql")
    void load_rooms() {
        List<Room> rooms = roomQueryPersistenceAdapter.loadRooms(UserId.make(1L));
        List<RoomEntity> roomEntities = roomQueryRepository.findRoomsByUserIdsContains(UserIdJpaVO.make(1L));

        assertEquals(rooms.size(), 3L);
        assertEquals(rooms.size(), roomEntities.size());
    }

    @ParameterizedTest
    @DisplayName("룸 조회 테스트")
    @Sql("RoomQueryPersistenceAdapterTest.sql")
    @ValueSource(longs = {1L, 2L, 3L})
    void load_room(Long roomId) {
        Room room = roomQueryPersistenceAdapter.loadByRoomId(RoomId.make(roomId));
        RoomEntity roomEntity = roomQueryRepository.findById(RoomIdJpaVO.make(roomId))
                .orElseThrow(NotFoundDataException::new);

        assertEquals(room.getRoomId().getId(), roomEntity.getRoomIdJpaVO().getId());
        assertEquals(room.getProfileInfo().getName(), roomEntity.getRoomInfo().getName());
        assertEquals(room.getProfileInfo().getProfile(), roomEntity.getRoomInfo().getProfile());
    }

    @Test
    @DisplayName("룸 Id 리스트 조회 테스트")
    @Sql("RoomQueryPersistenceAdapterTest.sql")
    void load_room_ids() {
        List<Long> roomIds = roomQueryPersistenceAdapter.loadRoomIds(UserId.make(1L));
        List<RoomIdJpaVO> roomIdJpaVOS = roomQueryRepository.findRoomIdsByUserIDsContains(
                UserIdJpaVO.make(1L));

        assertEquals(roomIds.size(), 3L);
        assertEquals(roomIdJpaVOS.size(), roomIds.size());
    }
}
package harmony.communityservice.room.adapter.out.persistence;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import harmony.communityservice.common.exception.NotFoundDataException;
import harmony.communityservice.room.domain.Room;
import harmony.communityservice.room.domain.RoomUser;
import harmony.communityservice.user.domain.User.UserId;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
@EnableTransactionManagement
class RoomCommandPersistenceAdapterTest {

    @Autowired
    RoomCommandPersistenceAdapter roomCommandPersistenceAdapter;

    @Autowired
    RoomCommandRepository roomCommandRepository;

    @Test
    @DisplayName("room 등록 JPA 테스트")
    void register_room() {
        RoomUser first = RoomUser.make(UserId.make(1L));
        RoomUser second = RoomUser.make(UserId.make(2L));
        Room room = Room.builder()
                .name("testRoom")
                .profile("example")
                .roomUsers(List.of(first, second))
                .build();

        roomCommandPersistenceAdapter.register(room);
        RoomEntity roomEntity = roomCommandRepository.findById(RoomIdJpaVO.make(1L))
                .orElseThrow(NotFoundDataException::new);

        assertNull(room.getRoomUsers().get(0).getRoomUserId());
        assertEquals(room.getProfileInfo().getProfile(), roomEntity.getRoomInfo().getProfile());
        assertEquals(2L, room.getRoomUsers().size());
        assertEquals(roomEntity.getId(), RoomIdJpaVO.make(1L));
        roomEntity.getRoomUserEntities()
                .forEach(roomUser -> assertThat(roomUser.getRoomUserIdJpaVO().longValue()).isBetween(1L,2L));
        roomEntity.getRoomUserEntities()
                .forEach(roomUser -> assertThat(roomUser.getUserId().getId()).isBetween(1L,2L));
    }

    @Test
    @DisplayName("room 삭제 DB 테스트")
    void delete_room() {
        RoomUser first = RoomUser.make(UserId.make(1L));
        RoomUser second = RoomUser.make(UserId.make(2L));
        Room room = Room.builder()
                .name("testRoom")
                .profile("example")
                .roomUsers(List.of(first, second))
                .build();
        roomCommandPersistenceAdapter.register(room);

        roomCommandPersistenceAdapter.delete(UserId.make(1L), UserId.make(2L));

        assertThrows(NotFoundDataException.class,
                () -> roomCommandRepository.findById(RoomIdJpaVO.make(2L)).orElseThrow(NotFoundDataException::new));
    }
}
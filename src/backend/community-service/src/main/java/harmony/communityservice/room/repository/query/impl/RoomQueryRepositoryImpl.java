package harmony.communityservice.room.repository.query.impl;

import harmony.communityservice.room.domain.Room;
import harmony.communityservice.room.repository.query.RoomQueryRepository;
import harmony.communityservice.room.repository.query.jpa.JpaRoomQueryRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RoomQueryRepositoryImpl implements RoomQueryRepository {

    private final JpaRoomQueryRepository jpaRoomQueryRepository;

    @Override
    public Optional<Room> findByRoomId(long roomId) {
        return jpaRoomQueryRepository.findById(roomId);
    }

    @Override
    public List<Room> findRoomsByUserIdsContains(Long userId) {
        return jpaRoomQueryRepository.findRoomsByUserIdsContains(userId);
    }
}

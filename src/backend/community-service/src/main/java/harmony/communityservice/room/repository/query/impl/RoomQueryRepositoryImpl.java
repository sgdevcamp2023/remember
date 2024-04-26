package harmony.communityservice.room.repository.query.impl;

import harmony.communityservice.room.domain.Room;
import harmony.communityservice.room.domain.RoomId;
import harmony.communityservice.room.repository.query.RoomQueryRepository;
import harmony.communityservice.room.repository.query.jpa.JpaRoomQueryRepository;
import harmony.communityservice.user.domain.UserId;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RoomQueryRepositoryImpl implements RoomQueryRepository {

    private final JpaRoomQueryRepository jpaRoomQueryRepository;

    @Override
    public Optional<Room> findByRoomId(RoomId roomId) {
        return jpaRoomQueryRepository.findById(roomId);
    }

    @Override
    public List<Room> findRoomsByUserIdsContains(UserId userId) {
        return jpaRoomQueryRepository.findRoomsByUserIdsContains(userId);
    }
}

package harmony.communityservice.community.query.repository.impl;

import harmony.communityservice.community.domain.Room;
import harmony.communityservice.community.query.repository.RoomQueryRepository;
import harmony.communityservice.community.query.repository.jpa.JpaRoomQueryRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RoomQueryRepositoryImpl implements RoomQueryRepository {

    private final JpaRoomQueryRepository jpaRoomQueryRepository;

    @Override
    public Optional<Room> findByRoomId(long roomId) {
        return jpaRoomQueryRepository.findById(roomId);
    }
}

package harmony.communityservice.room.repository.command.impl;

import harmony.communityservice.room.repository.command.RoomCommandRepository;
import harmony.communityservice.room.repository.command.jpa.JpaRoomCommandRepository;
import harmony.communityservice.room.domain.Room;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RoomCommandRepositoryImpl implements RoomCommandRepository {

    private final JpaRoomCommandRepository jpaRoomCommandRepository;

    @Override
    public void save(Room room) {
        jpaRoomCommandRepository.save(room);
    }

    @Override
    public void deleteRoomByUserIds(Long first, Long second) {
        jpaRoomCommandRepository.deleteRoomByUserIdsContainingAndUserIdsContaining(first, second);
    }

}

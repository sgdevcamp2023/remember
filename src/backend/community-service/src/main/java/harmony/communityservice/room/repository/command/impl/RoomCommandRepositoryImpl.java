package harmony.communityservice.room.repository.command.impl;

import harmony.communityservice.room.domain.Room;
import harmony.communityservice.room.domain.RoomId;
import harmony.communityservice.room.repository.command.RoomCommandRepository;
import harmony.communityservice.room.repository.command.jpa.JpaRoomCommandRepository;
import harmony.communityservice.user.domain.UserId;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RoomCommandRepositoryImpl implements RoomCommandRepository {

    private final JpaRoomCommandRepository jpaRoomCommandRepository;

    @Override
    public void save(Room room) {
        jpaRoomCommandRepository.save(room);
    }

    @Override
    public void deleteRoomByUserIds(UserId first, UserId second) {
        jpaRoomCommandRepository.deleteRoomByUserIds(first, second);
    }

    @Override
    public Optional<Room> findById(RoomId roomId) {
        return jpaRoomCommandRepository.findById(roomId);
    }

}

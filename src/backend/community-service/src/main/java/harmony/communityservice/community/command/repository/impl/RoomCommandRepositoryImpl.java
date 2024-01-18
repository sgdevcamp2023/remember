package harmony.communityservice.community.command.repository.impl;

import harmony.communityservice.community.command.repository.RoomCommandRepository;
import harmony.communityservice.community.command.repository.jpa.JpaRoomCommandRepository;
import harmony.communityservice.community.domain.Room;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RoomCommandRepositoryImpl implements RoomCommandRepository {

    private final JpaRoomCommandRepository jpaRoomCommandRepository;

    @Override
    public void save(Room room) {
        jpaRoomCommandRepository.save(room);
    }
}

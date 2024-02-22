package harmony.communityservice.community.command.repository.impl;

import harmony.communityservice.community.command.repository.RoomUserCommandRepository;
import harmony.communityservice.community.command.repository.jpa.JpaRoomUserCommandRepository;
import harmony.communityservice.community.domain.RoomUser;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RoomUserCommandRepositoryImpl implements RoomUserCommandRepository {

    private final JpaRoomUserCommandRepository jpaRoomUserCommandRepository;

    @Override
    public void save(RoomUser roomUser) {
        jpaRoomUserCommandRepository.save(roomUser);
    }

    @Override
    public void deleteByRoomUser(RoomUser roomUser) {
        jpaRoomUserCommandRepository.delete(roomUser);
    }
}

package harmony.communityservice.community.command.service.impl;

import harmony.communityservice.community.command.repository.RoomUserCommandRepository;
import harmony.communityservice.community.command.service.RoomUserCommandService;
import harmony.communityservice.community.domain.Room;
import harmony.communityservice.community.domain.RoomUser;
import harmony.communityservice.community.domain.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RoomUserCommandServiceImpl implements RoomUserCommandService {

    private final RoomUserCommandRepository roomUserCommandRepository;

    @Override
    public void register(Room room, User user) {
        RoomUser roomUser = RoomUser.make(room, user);
        roomUserCommandRepository.save(roomUser);
    }

    @Override
    public void delete(RoomUser roomUser) {
        roomUserCommandRepository.delete(roomUser);
    }
}

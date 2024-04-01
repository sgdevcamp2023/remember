package harmony.communityservice.community.command.service.impl;

import harmony.communityservice.community.command.dto.DeleteRoomRequest;
import harmony.communityservice.community.command.dto.RegisterRoomRequest;
import harmony.communityservice.community.command.repository.RoomCommandRepository;
import harmony.communityservice.community.command.service.RoomCommandService;
import harmony.communityservice.community.command.service.RoomUserCommandService;
import harmony.communityservice.community.domain.Room;
import harmony.communityservice.community.domain.RoomUser;
import harmony.communityservice.community.domain.User;
import harmony.communityservice.community.mapper.ToRoomMapper;
import harmony.communityservice.community.query.service.UserQueryService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RoomCommandServiceImpl implements RoomCommandService {

    private final RoomCommandRepository roomCommandRepository;
    private final UserQueryService userQueryService;
    private final RoomUserCommandService roomUserCommandService;

    @Override
    public void register(RegisterRoomRequest registerRoomRequest) {
        Room room = ToRoomMapper.convert(registerRoomRequest);
        roomCommandRepository.save(room);
        registerRoomRequest.members().stream()
                .map(userQueryService::searchByUserId)
                .forEach(user -> roomUserCommandService.register(room, user));
    }

    @Override
    public void delete(DeleteRoomRequest deleteRoomRequest) {
        List<RoomUser> firstRooms = userQueryService.searchByUserId(deleteRoomRequest.firstUser()).getRoomUsers();
        List<RoomUser> secondRooms = userQueryService.searchByUserId(deleteRoomRequest.secondUser()).getRoomUsers();
        Optional<RoomUser> firstRoomUser = firstRooms.stream()
                .flatMap(first -> secondRooms.stream()
                        .filter(second -> first.getRoom().getRoomId()
                                .equals(second.getRoom().getRoomId())))
                .findFirst();
        firstRoomUser.ifPresent(roomUser -> {
            roomUserCommandService.delete(roomUser);
            long roomId = roomUser.getRoom().getRoomId();
            roomCommandRepository.deleteByRoomId(roomId);
            secondRooms.stream()
                    .filter(user -> user.getRoom().getRoomId() == roomId)
                    .findFirst()
                    .ifPresent(roomUserCommandService::delete);
        });
    }
}

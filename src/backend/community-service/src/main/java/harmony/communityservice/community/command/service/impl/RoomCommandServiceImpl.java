package harmony.communityservice.community.command.service.impl;

import harmony.communityservice.community.command.dto.DeleteRoomRequest;
import harmony.communityservice.community.command.dto.RegisterRoomRequest;
import harmony.communityservice.community.command.repository.RoomCommandRepository;
import harmony.communityservice.community.command.service.RoomCommandService;
import harmony.communityservice.community.command.service.RoomUserCommandService;
import harmony.communityservice.community.domain.Room;
import harmony.communityservice.community.domain.RoomUser;
import harmony.communityservice.community.mapper.ToRoomMapper;
import harmony.communityservice.community.query.service.UserQueryService;
import java.util.List;
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
        List<RoomUser> firstRoomUsers = userQueryService.searchByUserId(deleteRoomRequest.firstUser()).getRoomUsers();
        List<RoomUser> secondRoomUsers = userQueryService.searchByUserId(deleteRoomRequest.secondUser()).getRoomUsers();
        RoomUser firstRoomUser = findFirstRoomUser(firstRoomUsers, secondRoomUsers);
        if (firstRoomUser != null) {
            roomUserCommandService.delete(firstRoomUser);
            long roomId = deleteRoom(firstRoomUser);
            deleteSecondRoomUser(secondRoomUsers, roomId);
        }
    }

    private long deleteRoom(RoomUser firstRoomUser) {
        long roomId = firstRoomUser.getRoom().getRoomId();
        roomCommandRepository.deleteByRoomId(roomId);
        return roomId;
    }

    private void deleteSecondRoomUser(List<RoomUser> secondRoomUsers, long roomId) {
        for (RoomUser secondRoomUser : secondRoomUsers) {
            if (secondRoomUser.sameRoomId(roomId)) {
                roomUserCommandService.delete(secondRoomUser);
                break;
            }
        }
    }

    private RoomUser findFirstRoomUser(List<RoomUser> firstRoomUsers, List<RoomUser> secondRoomUsers) {
        for (RoomUser firstRoomUser : firstRoomUsers) {
            for (RoomUser secondRoomUser : secondRoomUsers) {
                if (firstRoomUser.sameRoomId(secondRoomUser.getRoom().getRoomId())) {
                    return firstRoomUser;
                }
            }
        }
        return null;
    }
}

package harmony.communityservice.community.query.service.impl;

import harmony.communityservice.community.domain.RoomUser;
import harmony.communityservice.community.domain.User;
import harmony.communityservice.community.mapper.ToRoomResponseDtoMapper;
import harmony.communityservice.community.query.dto.RoomResponseDto;
import harmony.communityservice.community.query.dto.RoomsResponseDto;
import harmony.communityservice.community.query.service.RoomQueryService;
import harmony.communityservice.community.query.service.UserQueryService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RoomQueryServiceImpl implements RoomQueryService {

    private final UserQueryService userQueryService;

    @Override
    public RoomsResponseDto searchRoom(long userId) {
        User findUser = userQueryService.findUser(userId);
        List<RoomResponseDto> roomResponseDtos = findUser.getRoomUsers().stream()
                .map(RoomUser::getRoom)
                .map(ToRoomResponseDtoMapper::convert)
                .collect(Collectors.toList());
        return new RoomsResponseDto(roomResponseDtos);
    }
}

package harmony.communityservice.community.query.service.impl;

import harmony.communityservice.common.dto.SearchDmUserStateFeignResponse;
import harmony.communityservice.common.exception.NotFoundDataException;
import harmony.communityservice.common.feign.UserStatusClient;
import harmony.communityservice.community.domain.Room;
import harmony.communityservice.community.domain.RoomUser;
import harmony.communityservice.community.domain.User;
import harmony.communityservice.community.mapper.ToRoomResponseDtoMapper;
import harmony.communityservice.community.mapper.ToUserIdsMapper;
import harmony.communityservice.community.mapper.ToSearchUserStateResponseMapper;
import harmony.communityservice.community.query.dto.SearchUserStatusInDmRoomRequest;
import harmony.communityservice.community.query.dto.SearchRoomResponse;
import harmony.communityservice.community.query.dto.SearchRoomsResponse;
import harmony.communityservice.community.query.dto.SearchUserStateResponse;
import harmony.communityservice.community.query.repository.RoomQueryRepository;
import harmony.communityservice.community.query.service.RoomQueryService;
import harmony.communityservice.community.query.service.UserQueryService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RoomQueryServiceImpl implements RoomQueryService {

    private final UserQueryService userQueryService;

    private final RoomQueryRepository roomQueryRepository;
    private final UserStatusClient userStatusClient;

    @Override
    public SearchRoomsResponse searchList(long userId) {
        User findUser = userQueryService.searchByUserId(userId);
        List<SearchRoomResponse> roomResponseDtos = findUser.getRoomUsers().stream()
                .map(RoomUser::getRoom)
                .map(ToRoomResponseDtoMapper::convert)
                .collect(Collectors.toList());
        return new SearchRoomsResponse(roomResponseDtos);
    }

    @Override
    public Map<Long, ?> searchUserStatesInRoom(long roomId) {
        Room findRoom = roomQueryRepository.findByRoomId(roomId).orElseThrow(NotFoundDataException::new);
        List<User> users = findRoom.getRoomUsers().stream()
                .map(RoomUser::getUser).toList();
        List<Long> userIds = users.stream()
                .map(ToUserIdsMapper::convert).toList();
        SearchUserStatusInDmRoomRequest requestDto = new SearchUserStatusInDmRoomRequest(userIds);
        SearchDmUserStateFeignResponse stateFeignResponseDto = userStatusClient.getCommunityUsersState(requestDto);
        Map<Long, String> connectionStates = stateFeignResponseDto.getConnectionStates();
        Map<Long, SearchUserStateResponse> userStates = new HashMap<>();
        for (User user : users) {
            SearchUserStateResponse userStateResponseDto = ToSearchUserStateResponseMapper.convert(user, connectionStates.get(user.getUserId()));
            userStates.put(user.getUserId(), userStateResponseDto);

        }
        return userStates;
    }
}

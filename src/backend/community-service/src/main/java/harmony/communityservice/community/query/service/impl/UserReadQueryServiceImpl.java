package harmony.communityservice.community.query.service.impl;

import harmony.communityservice.common.dto.SearchUserStateInGuildAndRoomFeignResponse;
import harmony.communityservice.common.exception.NotFoundDataException;
import harmony.communityservice.common.feign.UserStatusClient;
import harmony.communityservice.community.domain.UserRead;
import harmony.communityservice.community.query.dto.SearchUserStateResponse;
import harmony.communityservice.community.query.dto.SearchUserStatesInGuildResponse;
import harmony.communityservice.community.query.dto.SearchUserStatesInGuildRequest;
import harmony.communityservice.community.query.repository.UserReadQueryRepository;
import harmony.communityservice.community.query.service.UserReadQueryService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class UserReadQueryServiceImpl implements UserReadQueryService {

    private final UserStatusClient userStatusClient;
    private final UserReadQueryRepository userReadQueryRepository;

    private static Map<Long, SearchUserStateResponse> makeUserStatesInGuild(
            List<SearchUserStateResponse> stateResponses, SearchUserStateInGuildAndRoomFeignResponse userState) {
        stateResponses.forEach(dto -> dto.modifyState(userState.getConnectionStates().get(dto.getUserId())));
        return stateResponses.stream()
                .collect(Collectors.toMap(SearchUserStateResponse::getUserId, dto -> dto));
    }

    @Override
    public void existsByUserIdAndGuildId(long userId, long guildId) {
        if (!userReadQueryRepository.existByUserIdAndGuildId(userId, guildId)) {
            throw new NotFoundDataException();
        }
    }

    @Override
    public UserRead searchByUserIdAndGuildId(long userId, long guildId) {
        return userReadQueryRepository.findByUserIdAndGuildId(userId, guildId).orElseThrow(NotFoundDataException::new);
    }

    @Override
    public List<UserRead> searchListByUserId(long userId) {
        return userReadQueryRepository.findUserReadsByUserId(userId);
    }

    @Override
    public SearchUserStatesInGuildResponse searchUserStatesInGuild(long guildId, long userId) {
        existsByUserIdAndGuildId(userId, guildId);
        List<SearchUserStateResponse> responseDtos = makeSearchUserStateResponses(guildId);
        List<Long> userIds = responseDtos.stream()
                .map(SearchUserStateResponse::getUserId)
                .collect(Collectors.toList());
        SearchUserStatesInGuildRequest userStatusRequestDto = new SearchUserStatesInGuildRequest(guildId, userIds);
        SearchUserStateInGuildAndRoomFeignResponse userState = userStatusClient.userStatus(userStatusRequestDto);
        log.info("{}", userState);
        Map<Long, SearchUserStateResponse> guildStates = makeUserStatesInGuild(
                responseDtos, userState);
        Map<Long, Map<Long, ?>> voiceChannelStates = getUserStatesInVoiceChannel(guildId, userState);
        return new SearchUserStatesInGuildResponse(guildStates, voiceChannelStates);
    }

    private List<SearchUserStateResponse> makeSearchUserStateResponses(long guildId) {
        List<UserRead> findUserReads = userReadQueryRepository.findUserReadsByGuildId(guildId);
        return findUserReads.stream()
                .map(findUserRead -> {
                    return SearchUserStateResponse.builder()
                            .userName(findUserRead.getNickname())
                            .profile(findUserRead.getProfile())
                            .userId(findUserRead.getUserId())
                            .build();
                }).toList();
    }

    private Map<Long, Map<Long, ?>> getUserStatesInVoiceChannel(long guildId, SearchUserStateInGuildAndRoomFeignResponse userState) {
        Map<Long, Set<Long>> channelStates = userState.getChannelStates();
        Map<Long, Map<Long, ?>> voiceChannelStates = new HashMap<>();
        for (Long channelId : channelStates.keySet()) {
            Set<Long> voiceUserIds = channelStates.get(channelId);
            Map<Long, UserRead> userReads = new HashMap<>();
            for (Long voiceUserId : voiceUserIds) {
                UserRead findUserRead = searchByUserIdAndGuildId(voiceUserId, guildId);
                userReads.put(findUserRead.getUserId(), findUserRead);
            }
            voiceChannelStates.put(channelId, userReads);
        }
        return voiceChannelStates;
    }
}
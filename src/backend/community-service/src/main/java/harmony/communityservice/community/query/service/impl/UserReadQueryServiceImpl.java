package harmony.communityservice.community.query.service.impl;

import harmony.communityservice.common.dto.SearchUserReadRequest;
import harmony.communityservice.common.dto.SearchUserStateInGuildAndRoomFeignResponse;
import harmony.communityservice.common.dto.VerifyGuildMemberRequest;
import harmony.communityservice.common.exception.NotFoundDataException;
import harmony.communityservice.common.feign.UserStatusClient;
import harmony.communityservice.community.domain.UserRead;
import harmony.communityservice.community.mapper.ToSearchUserStateResponseMapper;
import harmony.communityservice.community.query.dto.SearchUserStateResponse;
import harmony.communityservice.community.query.dto.SearchUserStatesInGuildRequest;
import harmony.communityservice.community.query.dto.SearchUserStatesInGuildResponse;
import harmony.communityservice.community.query.repository.UserReadQueryRepository;
import harmony.communityservice.community.query.service.UserReadQueryService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserReadQueryServiceImpl implements UserReadQueryService {

    private final UserStatusClient userStatusClient;
    private final UserReadQueryRepository userReadQueryRepository;

    @Override
    public void existsByUserIdAndGuildId(VerifyGuildMemberRequest verifyGuildMemberRequest) {
        if (!userReadQueryRepository.existByUserIdAndGuildId(verifyGuildMemberRequest.userId(),
                verifyGuildMemberRequest.guildId())) {
            throw new NotFoundDataException();
        }
    }

    @Override
    public UserRead searchByUserIdAndGuildId(SearchUserReadRequest searchUserReadRequest) {
        return userReadQueryRepository.findByUserIdAndGuildId(searchUserReadRequest.userId(),
                searchUserReadRequest.guildId()).orElseThrow(NotFoundDataException::new);
    }

    @Override
    public List<UserRead> searchListByUserId(long userId) {
        return userReadQueryRepository.findUserReadsByUserId(userId);
    }

    @Override
    public SearchUserStatesInGuildResponse searchUserStatesInGuild(long guildId, long userId) {
        existsByUserIdAndGuildId(new VerifyGuildMemberRequest(userId, guildId));
        List<SearchUserStateResponse> searchUserStateResponses = makeSearchUserStateResponses(guildId);
        SearchUserStateInGuildAndRoomFeignResponse userState = getSearchUserStateInGuildAndRoomFeignResponse(
                guildId, searchUserStateResponses);
        Map<Long, SearchUserStateResponse> guildStates = makeUserStatesInGuild(
                searchUserStateResponses, userState);
        Map<Long, Map<Long, ?>> voiceChannelStates = getUserStatesInVoiceChannel(guildId, userState);
        return new SearchUserStatesInGuildResponse(guildStates, voiceChannelStates);
    }

    private List<SearchUserStateResponse> makeSearchUserStateResponses(long guildId) {
        List<UserRead> targetUserReads = userReadQueryRepository.findUserReadsByGuildId(guildId);
        return targetUserReads
                .stream()
                .map(ToSearchUserStateResponseMapper::convert)
                .toList();
    }

    private SearchUserStateInGuildAndRoomFeignResponse getSearchUserStateInGuildAndRoomFeignResponse(long guildId,
                                                                                                     List<SearchUserStateResponse> searchUserStateResponses) {
        List<Long> userIds = searchUserStateResponses
                .stream()
                .map(SearchUserStateResponse::getUserId)
                .collect(Collectors.toList());
        return userStatusClient.userStatus(new SearchUserStatesInGuildRequest(guildId, userIds));
    }

    private Map<Long, SearchUserStateResponse> makeUserStatesInGuild(
            List<SearchUserStateResponse> stateResponses, SearchUserStateInGuildAndRoomFeignResponse userState) {
        stateResponses.forEach(state -> state.modifyState(userState.getConnectionStates().get(state.getUserId())));
        return stateResponses.stream()
                .collect(Collectors.toMap(SearchUserStateResponse::getUserId, state -> state));
    }

    private Map<Long, Map<Long, ?>> getUserStatesInVoiceChannel(long guildId,
                                                                SearchUserStateInGuildAndRoomFeignResponse userState) {
        Map<Long, Set<Long>> channelStates = userState.getChannelStates();
        Map<Long, Map<Long, ?>> voiceChannelStates = new HashMap<>();
        for (Long channelId : channelStates.keySet()) {
            Set<Long> voiceUserIds = channelStates.get(channelId);
            Map<Long, UserRead> userReads = getUserReads(guildId, voiceUserIds);
            voiceChannelStates.put(channelId, userReads);
        }
        return voiceChannelStates;
    }

    private Map<Long, UserRead> getUserReads(long guildId, Set<Long> voiceUserIds) {
        Map<Long, UserRead> userReads = new HashMap<>();
        for (Long voiceUserId : voiceUserIds) {
            UserRead findUserRead = searchByUserIdAndGuildId(new SearchUserReadRequest(voiceUserId, guildId));
            userReads.put(findUserRead.getUserId(), findUserRead);
        }
        return userReads;
    }
}
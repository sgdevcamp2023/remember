package harmony.communityservice.guild.guild.service.query.impl;

import harmony.communityservice.common.annotation.AuthorizeGuildMember;
import harmony.communityservice.common.client.UserStatusClient;
import harmony.communityservice.common.dto.SearchParameterMapperRequest;
import harmony.communityservice.common.dto.SearchUserReadRequest;
import harmony.communityservice.common.dto.SearchUserStateInGuildAndRoomFeignResponse;
import harmony.communityservice.common.exception.NotFoundDataException;
import harmony.communityservice.guild.domain.Guild;
import harmony.communityservice.guild.guild.dto.SearchGuildInvitationCodeRequest;
import harmony.communityservice.guild.guild.dto.SearchUserStatesInGuildResponse;
import harmony.communityservice.guild.guild.mapper.ToInvitationCodeMapper;
import harmony.communityservice.guild.guild.repository.query.GuildQueryRepository;
import harmony.communityservice.guild.guild.service.query.GuildQueryService;
import harmony.communityservice.room.dto.SearchUserStateResponse;
import harmony.communityservice.room.mapper.ToSearchUserStateResponseMapper;
import harmony.communityservice.user.domain.UserRead;
import harmony.communityservice.guild.guild.dto.SearchUserStatesInGuildRequest;
import harmony.communityservice.user.service.query.UserReadQueryService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GuildQueryServiceImpl implements GuildQueryService {

    private final GuildQueryRepository guildQueryRepository;
    private final UserReadQueryService userReadQueryService;
    private final UserStatusClient userStatusClient;

    @Override
    @AuthorizeGuildMember
    public String searchInvitationCode(SearchGuildInvitationCodeRequest searchGuildInvitationCodeRequest) {
        Guild guild = guildQueryRepository.findById(searchGuildInvitationCodeRequest.guildId())
                .orElseThrow(NotFoundDataException::new);
        return ToInvitationCodeMapper.convert(guild.getInviteCode(), searchGuildInvitationCodeRequest.userId(),
                searchGuildInvitationCodeRequest.guildId());
    }

    @Override
    public void existsByGuildIdAndManagerId(Long guildId, Long managerId) {
        if (!guildQueryRepository.existsByGuildIdAndManagerId(guildId, managerId)) {
            throw new NotFoundDataException("관리자만 가능합니다");
        }
    }

    @Override
    @AuthorizeGuildMember
    public SearchUserStatesInGuildResponse searchUserStatesInGuild(
            SearchParameterMapperRequest searchParameterMapperRequest) {
        Long guildId = searchParameterMapperRequest.guildId();
        List<SearchUserStateResponse> searchUserStateResponses = makeSearchUserStateResponses(
                guildId);
        SearchUserStateInGuildAndRoomFeignResponse userState = getSearchUserStateInGuildAndRoomFeignResponse(
                guildId, searchUserStateResponses);
        Map<Long, SearchUserStateResponse> guildStates = makeUserStatesInGuild(
                searchUserStateResponses, userState);
        Map<Long, Map<Long, ?>> voiceChannelStates = getUserStatesInVoiceChannel(guildId, userState);
        return new SearchUserStatesInGuildResponse(guildStates, voiceChannelStates);
    }

    private List<SearchUserStateResponse> makeSearchUserStateResponses(long guildId) {
        List<UserRead> targetUserReads = userReadQueryService.searchListByGuildId(guildId);
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
            UserRead findUserRead = userReadQueryService.searchByUserIdAndGuildId(
                    new SearchUserReadRequest(voiceUserId, guildId));
            userReads.put(findUserRead.getUserId(), findUserRead);
        }
        return userReads;
    }
}
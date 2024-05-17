package harmony.communityservice.guild.guild.application.service;

import harmony.communityservice.common.annotation.AuthorizeGuildMember;
import harmony.communityservice.common.annotation.UseCase;
import harmony.communityservice.common.client.UserStatusClient;
import harmony.communityservice.common.dto.SearchUserStateInGuildAndRoomFeignResponse;
import harmony.communityservice.guild.guild.application.port.in.LoadGuildReadCommand;
import harmony.communityservice.guild.guild.application.port.in.LoadGuildReadsQuery;
import harmony.communityservice.guild.guild.application.port.in.LoadGuildUserStatesCommand;
import harmony.communityservice.guild.guild.application.port.in.LoadGuildUserStatesQuery;
import harmony.communityservice.guild.guild.application.port.in.LoadGuildUserStatesResponse;
import harmony.communityservice.guild.guild.application.port.in.LoadInvitationCodeCommand;
import harmony.communityservice.guild.guild.application.port.in.LoadInvitationCodeQuery;
import harmony.communityservice.guild.guild.application.port.in.LoadVoiceUserQuery;
import harmony.communityservice.guild.guild.application.port.in.VerifyGuildManagerQuery;
import harmony.communityservice.guild.guild.application.port.out.LoadGuildPort;
import harmony.communityservice.guild.guild.application.port.out.VerifyGuildManagerPort;
import harmony.communityservice.guild.guild.domain.Guild;
import harmony.communityservice.guild.guild.domain.Guild.GuildId;
import harmony.communityservice.guild.guild.domain.GuildRead;
import harmony.communityservice.guild.guild.adapter.in.web.SearchUserStatesInGuildRequest;
import harmony.communityservice.room.application.port.in.SearchUserStateResponse;
import harmony.communityservice.room.mapper.ToSearchUserStateResponseMapper;
import harmony.communityservice.user.domain.User.UserId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional(readOnly = true)
class GuildQueryService implements LoadInvitationCodeQuery, VerifyGuildManagerQuery, LoadGuildUserStatesQuery {

    private final UserStatusClient userStatusClient;
    private final LoadGuildPort loadGuildPort;
    private final VerifyGuildManagerPort verifyGuildManagerPort;
    private final LoadGuildReadsQuery loadGuildReadsUseCase;
    private final LoadVoiceUserQuery loadVoiceUserQuery;

    @Override
    @AuthorizeGuildMember
    public String load(LoadInvitationCodeCommand searchGuildInvitationCodeCommand) {
        Guild guild = loadGuildPort.loadById(GuildId.make(searchGuildInvitationCodeCommand.guildId()));
        return guild.getInvitationCode() + "." + searchGuildInvitationCodeCommand.userId() + "."
                + searchGuildInvitationCodeCommand.guildId();
    }

    @Override
    public void verify(Long guildId, Long managerId) {
        verifyGuildManagerPort.verify(GuildId.make(guildId), UserId.make(managerId));
    }

    @Override
    @AuthorizeGuildMember
    public LoadGuildUserStatesResponse load(LoadGuildUserStatesCommand loadGuildUserStatesCommand) {
        Long guildId = loadGuildUserStatesCommand.getGuildId();
        List<SearchUserStateResponse> searchUserStateResponses = makeSearchUserStateResponses(guildId);
        SearchUserStateInGuildAndRoomFeignResponse userState = getSearchUserStateInGuildAndRoomFeignResponse(
                guildId, searchUserStateResponses);
        Map<Long, SearchUserStateResponse> guildStates = makeUserStatesInGuild(
                searchUserStateResponses, userState);
        Map<Long, Map<Long, ?>> voiceChannelStates = getUserStatesInVoiceChannel(guildId, userState);
        return new LoadGuildUserStatesResponse(guildStates, voiceChannelStates);
    }

    private List<SearchUserStateResponse> makeSearchUserStateResponses(long guildId) {
        List<GuildRead> guildReads = loadGuildReadsUseCase.loadList(guildId);
        return guildReads
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
            Map<Long, GuildRead> guildReads = getUserReads(guildId, voiceUserIds);
            voiceChannelStates.put(channelId, guildReads);
        }
        return voiceChannelStates;
    }

    private Map<Long, GuildRead> getUserReads(long guildId, Set<Long> voiceUserIds) {
        Map<Long, GuildRead> userReads = new HashMap<>();
        for (Long voiceUserId : voiceUserIds) {
            GuildRead guildRead = loadVoiceUserQuery.loadByUserIdAndGuildId(
                    new LoadGuildReadCommand(voiceUserId, guildId));
            userReads.put(guildRead.getUserId().getId(), guildRead);
        }
        return userReads;
    }

}
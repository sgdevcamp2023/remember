package harmony.communityservice.guild.guild.application.service;

import harmony.communityservice.common.annotation.AuthorizeGuildMember;
import harmony.communityservice.common.annotation.UseCase;
import harmony.communityservice.common.client.UserStatusClient;
import harmony.communityservice.common.dto.LoadUserStateInGuildAndChannelFeignResponse;
import harmony.communityservice.guild.guild.adapter.in.web.LoadUserStatesInGuildRequest;
import harmony.communityservice.guild.guild.application.port.in.LoadGuildReadsQuery;
import harmony.communityservice.guild.guild.application.port.in.LoadGuildUserStatesCommand;
import harmony.communityservice.guild.guild.application.port.in.LoadGuildUserStatesQuery;
import harmony.communityservice.guild.guild.application.port.in.LoadGuildUserStatesResponse;
import harmony.communityservice.guild.guild.application.port.in.LoadInvitationCodeCommand;
import harmony.communityservice.guild.guild.application.port.in.LoadInvitationCodeQuery;
import harmony.communityservice.guild.guild.application.port.in.VerifyGuildManagerQuery;
import harmony.communityservice.guild.guild.application.port.out.LoadGuildPort;
import harmony.communityservice.guild.guild.application.port.out.VerifyGuildManagerPort;
import harmony.communityservice.guild.guild.domain.Guild;
import harmony.communityservice.guild.guild.domain.Guild.GuildId;
import harmony.communityservice.guild.guild.domain.GuildRead;
import harmony.communityservice.room.application.port.in.LoadUserStateResponse;
import harmony.communityservice.room.application.service.SearchUserStateResponseMapper;
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
    private final LoadGuildReadsQuery loadGuildReadsQuery;

    @Override
    @AuthorizeGuildMember
    public String loadInvitationCode(LoadInvitationCodeCommand loadInvitationCodeCommand) {
        Guild guild = loadGuildPort.loadById(GuildId.make(loadInvitationCodeCommand.guildId()));
        return guild.getInvitationCode() + "." + loadInvitationCodeCommand.userId() + "."
                + loadInvitationCodeCommand.guildId();
    }

    @Override
    public void verify(Long guildId, Long managerId) {
        verifyGuildManagerPort.verify(GuildId.make(guildId), UserId.make(managerId));
    }

    @Override
    @AuthorizeGuildMember
    public LoadGuildUserStatesResponse load(LoadGuildUserStatesCommand loadGuildUserStatesCommand) {
        Long guildId = loadGuildUserStatesCommand.getGuildId();
        List<GuildRead> guildReads = loadGuildReadsQuery.loadList(guildId);
        List<LoadUserStateResponse> loadUserStateResponses = guildReads
                .stream()
                .map(SearchUserStateResponseMapper::convert)
                .toList();
        Map<Long, GuildRead> guildReadMap = getGuildReadMap(guildReads);
        LoadUserStateInGuildAndChannelFeignResponse userState = getSearchUserStateInGuildAndRoomFeignResponse(
                guildId, loadUserStateResponses);
        Map<Long, LoadUserStateResponse> guildStates = makeUserStatesInGuild(
                loadUserStateResponses, userState);
        Map<Long, Map<Long, ?>> voiceChannelStates = getUserStatesInVoiceChannel(userState, guildReadMap);
        return new LoadGuildUserStatesResponse(guildStates, voiceChannelStates);
    }

    private Map<Long, GuildRead> getGuildReadMap(List<GuildRead> guildReads) {
        Map<Long, GuildRead> guildReadMap = new HashMap<>();
        guildReads.forEach(guildRead -> guildReadMap.put(guildRead.getUserId().getId(), guildRead));
        return guildReadMap;
    }

    private LoadUserStateInGuildAndChannelFeignResponse getSearchUserStateInGuildAndRoomFeignResponse(long guildId,
                                                                                                      List<LoadUserStateResponse> searchUserStateResponses) {
        List<Long> userIds = searchUserStateResponses
                .stream()
                .map(LoadUserStateResponse::getUserId)
                .collect(Collectors.toList());
        return userStatusClient.getCommunityUsersState(new LoadUserStatesInGuildRequest(guildId, userIds));
    }

    private Map<Long, LoadUserStateResponse> makeUserStatesInGuild(
            List<LoadUserStateResponse> stateResponses, LoadUserStateInGuildAndChannelFeignResponse userState) {
        stateResponses.forEach(state -> state.modifyState(userState.getConnectionStates().get(state.getUserId())));
        return stateResponses.stream()
                .collect(Collectors.toMap(LoadUserStateResponse::getUserId, state -> state));
    }

    private Map<Long, Map<Long, ?>> getUserStatesInVoiceChannel(
            LoadUserStateInGuildAndChannelFeignResponse userState,
            Map<Long, GuildRead> guildReadMap) {
        Map<Long, Set<Long>> channelStates = userState.getChannelStates();
        Map<Long, Map<Long, ?>> voiceChannelStates = new HashMap<>();
        for (Long channelId : channelStates.keySet()) {
            Set<Long> voiceUserIds = channelStates.get(channelId);
            Map<Long, GuildRead> guildReads = getUserReads(voiceUserIds, guildReadMap);
            voiceChannelStates.put(channelId, guildReads);
        }
        return voiceChannelStates;
    }

    private Map<Long, GuildRead> getUserReads(Set<Long> voiceUserIds, Map<Long, GuildRead> guildReadMap) {
        Map<Long, GuildRead> userReads = new HashMap<>();
        for (Long voiceUserId : voiceUserIds) {
            GuildRead guildRead = guildReadMap.get(voiceUserId);
            userReads.put(guildRead.getUserId().getId(), guildRead);
        }
        return userReads;
    }

}
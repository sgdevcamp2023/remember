package harmony.communityservice.guild.guild.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;

import harmony.communityservice.common.client.UserStatusClient;
import harmony.communityservice.common.dto.LoadUserStateInGuildAndChannelFeignResponse;
import harmony.communityservice.domain.Threshold;
import harmony.communityservice.guild.guild.adapter.in.web.LoadUserStatesInGuildRequest;
import harmony.communityservice.guild.guild.application.port.in.LoadGuildReadsQuery;
import harmony.communityservice.guild.guild.application.port.in.LoadGuildUserStatesCommand;
import harmony.communityservice.guild.guild.application.port.in.LoadGuildUserStatesResponse;
import harmony.communityservice.guild.guild.application.port.in.LoadInvitationCodeCommand;
import harmony.communityservice.guild.guild.application.port.out.LoadGuildPort;
import harmony.communityservice.guild.guild.application.port.out.VerifyGuildManagerPort;
import harmony.communityservice.guild.guild.domain.Guild;
import harmony.communityservice.guild.guild.domain.Guild.GuildId;
import harmony.communityservice.guild.guild.domain.GuildRead;
import harmony.communityservice.guild.guild.domain.GuildRead.GuildReadId;
import harmony.communityservice.room.application.port.in.LoadUserStateResponse;
import harmony.communityservice.user.domain.User.UserId;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GuildQueryServiceTest {

    @Mock
    UserStatusClient userStatusClient;
    @Mock
    LoadGuildPort loadGuildPort;
    @Mock
    VerifyGuildManagerPort verifyGuildManagerPort;
    @Mock
    LoadGuildReadsQuery loadGuildReadsQuery;

    GuildQueryService guildQueryService;

    @BeforeEach
    void setting() {
        guildQueryService = new GuildQueryService(userStatusClient, loadGuildPort, verifyGuildManagerPort,
                loadGuildReadsQuery);
    }

    @Test
    @DisplayName("초대 코드 조회 테스트")
    void load_invitation_code() {
        assertNotNull(guildQueryService);

        Guild guild = Guild.builder()
                .guildId(GuildId.make(Threshold.MIN.getValue()))
                .name("test")
                .profile("http://cdn.com/test")
                .managerId(UserId.make(1L))
                .inviteCode("8c475c80ffe4401385a040344046abce")
                .build();
        LoadInvitationCodeCommand loadInvitationCodeCommand = new LoadInvitationCodeCommand(1L, 1L);
        given(loadGuildPort.loadById(GuildId.make(1L))).willReturn(guild);

        String invitationCode = guildQueryService.loadInvitationCode(loadInvitationCodeCommand);

        assertEquals(invitationCode, "8c475c80ffe4401385a040344046abce.1.1");

        then(loadGuildPort).should(times(1)).loadById(GuildId.make(1L));
    }

    @Test
    @DisplayName("길드 매니저 인증 테스트")
    void verify_guild_manager() {
        assertNotNull(guildQueryService);
        willDoNothing().given(verifyGuildManagerPort).verify(GuildId.make(1L), UserId.make(1L));

        guildQueryService.verify(1L, 1L);

        then(verifyGuildManagerPort).should(times(1)).verify(GuildId.make(1L), UserId.make(1L));
    }

    @Test
    @DisplayName("길드 유저 상태 조회 테스트")
    void load_guild_user_states() {
        assertNotNull(guildQueryService);
        GuildRead first = GuildRead.builder()
                .userProfile("http://user.com/test1")
                .userNickname("0chord")
                .guildId(GuildId.make(1L))
                .guildReadId(GuildReadId.make(1L))
                .profile("http://guild.com/test1")
                .name("first_guild")
                .userId(UserId.make(1L))
                .build();

        GuildRead second = GuildRead.builder()
                .userProfile("http://user.com/test2")
                .userNickname("0Chord")
                .guildId(GuildId.make(1L))
                .guildReadId(GuildReadId.make(2L))
                .profile("http://guild.com/test2")
                .name("first_guild")
                .userId(UserId.make(2L))
                .build();

        GuildRead third = GuildRead.builder()
                .userProfile("http://user.com/test3")
                .userNickname("yh")
                .guildId(GuildId.make(1L))
                .guildReadId(GuildReadId.make(3L))
                .profile("http://guild.com/test3")
                .name("first_guild")
                .userId(UserId.make(3L))
                .build();

        LoadUserStateResponse firstUserState = LoadUserStateResponse.builder()
                .state("ONLINE")
                .userName("0chord")
                .userId(1L)
                .profile("http://user.com/test1")
                .build();
        LoadUserStateResponse secondUserState = LoadUserStateResponse.builder()
                .state("ONLINE")
                .userName("0Chord")
                .userId(2L)
                .profile("http://user.com/test2")
                .build();

        LoadUserStateResponse thirdUserState = LoadUserStateResponse.builder()
                .state("ONLINE")
                .userName("yh")
                .userId(3L)
                .profile("http://user.com/test3")
                .build();

        List<GuildRead> guildReads = List.of(first, second, third);
        LoadUserStatesInGuildRequest loadUserStatesInGuildRequest = new LoadUserStatesInGuildRequest(1L,
                List.of(1L, 2L, 3L));
        Map<Long, LoadUserStateResponse> guildStates = Map.of(1L, firstUserState, 2L, secondUserState, 3L,
                thirdUserState);
        Map<Long, Map<Long, ?>> voiceChannelStates = Map.of(1L, Map.of(1L, first, 2L, second, 3L, third));
        Map<Long, String> connectionStates = Map.of(1L, "ONLINE", 2L, "ONLINE", 3L, "ONLINE");
        Map<Long, Set<Long>> channelStates = Map.of(1L, Set.of(1L, 2L, 3L));
        LoadGuildUserStatesCommand loadGuildUserStatesCommand = new LoadGuildUserStatesCommand(1L, 1L);
        given(loadGuildReadsQuery.loadList(1L)).willReturn(guildReads);
        given(userStatusClient.getCommunityUsersState(loadUserStatesInGuildRequest)).willReturn(
                new LoadUserStateInGuildAndChannelFeignResponse(channelStates, connectionStates));

        LoadGuildUserStatesResponse response = guildQueryService.load(loadGuildUserStatesCommand);
        assertEquals(guildStates,response.getGuildStates());
        assertEquals(voiceChannelStates,response.getVoiceChannelStates());

    }
}
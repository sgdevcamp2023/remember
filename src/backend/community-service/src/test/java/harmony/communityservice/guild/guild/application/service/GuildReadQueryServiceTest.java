package harmony.communityservice.guild.guild.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;

import harmony.communityservice.guild.guild.adapter.in.web.SearchGuildReadResponse;
import harmony.communityservice.guild.guild.application.port.in.LoadGuildReadCommand;
import harmony.communityservice.guild.guild.application.port.in.VerifyGuildMemberCommand;
import harmony.communityservice.guild.guild.application.port.out.LoadGuildIdsPort;
import harmony.communityservice.guild.guild.application.port.out.LoadGuildReadPort;
import harmony.communityservice.guild.guild.application.port.out.LoadGuildReadsPort;
import harmony.communityservice.guild.guild.application.port.out.VerifyGuildMemberPort;
import harmony.communityservice.guild.guild.domain.Guild.GuildId;
import harmony.communityservice.guild.guild.domain.GuildRead;
import harmony.communityservice.guild.guild.domain.GuildRead.GuildReadId;
import harmony.communityservice.user.domain.User.UserId;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GuildReadQueryServiceTest {

    @Mock
    LoadGuildReadsPort loadGuildReadsPort;
    @Mock
    LoadGuildReadPort loadGuildReadPort;
    @Mock
    VerifyGuildMemberPort verifyGuildMemberPort;
    @Mock
    LoadGuildIdsPort loadGuildIdsPort;

    GuildReadQueryService guildReadQueryService;

    @BeforeEach
    void setting() {
        guildReadQueryService = new GuildReadQueryService(loadGuildReadsPort, loadGuildReadPort, verifyGuildMemberPort,
                loadGuildIdsPort);
    }

    @Test
    @DisplayName("user가 속한 guild 리스트 조회 테스트")
    void load_guilds() {
        assertNotNull(guildReadQueryService);

        GuildRead first = GuildRead.builder()
                .userProfile("http://user.com/test")
                .userNickname("0chord")
                .guildId(GuildId.make(1L))
                .guildReadId(GuildReadId.make(1L))
                .profile("http://guild.com/test1")
                .name("first_guild")
                .userId(UserId.make(1L))
                .build();

        GuildRead second = GuildRead.builder()
                .userProfile("http://user.com/test")
                .userNickname("0Chord")
                .guildId(GuildId.make(2L))
                .guildReadId(GuildReadId.make(2L))
                .profile("http://guild.com/test2")
                .name("first_guild")
                .userId(UserId.make(1L))
                .build();

        GuildRead third = GuildRead.builder()
                .userProfile("http://user.com/test")
                .userNickname("yh")
                .guildId(GuildId.make(3L))
                .guildReadId(GuildReadId.make(3L))
                .profile("http://guild.com/test3")
                .name("first_guild")
                .userId(UserId.make(1L))
                .build();
        List<GuildRead> guildReads = List.of(first, second, third);

        SearchGuildReadResponse firstResponse = SearchGuildReadResponse.builder()
                .guildReadId(first.getGuildReadId().getId())
                .guildId(first.getGuildId().getId())
                .userId(first.getUserId().getId())
                .name(first.getProfileInfo().getName())
                .profile(first.getProfileInfo().getProfile())
                .build();

        SearchGuildReadResponse secondResponse = SearchGuildReadResponse.builder()
                .guildReadId(second.getGuildReadId().getId())
                .guildId(second.getGuildId().getId())
                .userId(second.getUserId().getId())
                .name(second.getProfileInfo().getName())
                .profile(second.getProfileInfo().getProfile())
                .build();

        SearchGuildReadResponse thirdResponse = SearchGuildReadResponse.builder()
                .guildReadId(third.getGuildReadId().getId())
                .guildId(third.getGuildId().getId())
                .userId(third.getUserId().getId())
                .name(third.getProfileInfo().getName())
                .profile(third.getProfileInfo().getProfile())
                .build();
        Map<Long, ?> resultMap = Map.of(1L, firstResponse, 2L,
                secondResponse, 3L, thirdResponse);
        given(loadGuildReadsPort.loadListByUserId(UserId.make(1L))).willReturn(guildReads);

        Map<Long, ?> longMap = guildReadQueryService.loadGuilds(1L);

        assertEquals(resultMap, longMap);

        then(loadGuildReadsPort).should(times(1)).loadListByUserId(UserId.make(1L));
    }

    @Test
    @DisplayName("유저가 속한 Guild의 GuildId 조회 테스트")
    void load_guild_ids() {
        assertNotNull(guildReadQueryService);

        List<GuildId> guildIds = List.of(GuildId.make(1L), GuildId.make(2L), GuildId.make(3L));
        given(loadGuildIdsPort.loadList(UserId.make(1L))).willReturn(guildIds);

        List<GuildId> result = guildReadQueryService.loadGuildIdsByUserId(UserId.make(1L));

        assertEquals(result, guildIds);
        then(loadGuildIdsPort).should(times(1)).loadList(UserId.make(1L));
    }

    @Test
    @DisplayName("guild에 user가 속해있는지 인증 테스트")
    void verify_user() {
        assertNotNull(guildReadQueryService);

        willDoNothing().given(verifyGuildMemberPort).verify(UserId.make(1L),
                GuildId.make(1L));

        guildReadQueryService.verify(new VerifyGuildMemberCommand(1L, 1L));

        then(verifyGuildMemberPort).should(times(1)).verify(UserId.make(1L),
                GuildId.make(1L));
    }

    @Test
    @DisplayName("guild에 속한 user가 리스트 조회 테스트")
    void load_list() {
        assertNotNull(guildReadQueryService);

        GuildRead first = GuildRead.builder()
                .userProfile("http://user.com/test1")
                .userNickname("0chord")
                .guildId(GuildId.make(1L))
                .guildReadId(GuildReadId.make(1L))
                .profile("http://guild.com/test")
                .name("first_guild")
                .userId(UserId.make(1L))
                .build();

        GuildRead second = GuildRead.builder()
                .userProfile("http://user.com/test2")
                .userNickname("0Chord")
                .guildId(GuildId.make(1L))
                .guildReadId(GuildReadId.make(2L))
                .profile("http://guild.com/test")
                .name("first_guild")
                .userId(UserId.make(2L))
                .build();

        GuildRead third = GuildRead.builder()
                .userProfile("http://user.com/test3")
                .userNickname("yh")
                .guildId(GuildId.make(1L))
                .guildReadId(GuildReadId.make(3L))
                .profile("http://guild.com/test")
                .name("first_guild")
                .userId(UserId.make(3L))
                .build();
        List<GuildRead> guildReads = List.of(first, second, third);
        given(loadGuildReadsPort.loadListByGuildId(GuildId.make(1L))).willReturn(guildReads);

        List<GuildRead> result = guildReadQueryService.loadList(1L);

        assertEquals(result, guildReads);
        then(loadGuildReadsPort).should(times(1)).loadListByGuildId(GuildId.make(1L));
    }

    @Test
    @DisplayName("guild user 조회 테스트")
    void load_guild_user() {
        assertNotNull(guildReadQueryService);
        GuildRead guildRead = GuildRead.builder()
                .userProfile("http://user.com/test1")
                .userNickname("0chord")
                .guildId(GuildId.make(1L))
                .guildReadId(GuildReadId.make(1L))
                .profile("http://guild.com/test")
                .name("first_guild")
                .userId(UserId.make(1L))
                .build();

        given(loadGuildReadPort.loadByUserIdAndGuildId(UserId.make(1L), GuildId.make(1L))).willReturn(guildRead);

        GuildRead result = guildReadQueryService.loadByUserIdAndGuildId(new LoadGuildReadCommand(1L, 1L));

        assertEquals(guildRead, result);
        then(loadGuildReadPort).should(times(1)).loadByUserIdAndGuildId(UserId.make(1L), GuildId.make(1L));
    }
}
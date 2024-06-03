package harmony.communityservice.guild.guild.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;

import harmony.communityservice.domain.Threshold;
import harmony.communityservice.guild.guild.application.port.in.ModifyGuildNicknameCommand;
import harmony.communityservice.guild.guild.application.port.in.RegisterGuildReadCommand;
import harmony.communityservice.guild.guild.application.port.out.DeleteGuildReadPort;
import harmony.communityservice.guild.guild.application.port.out.LoadGuildReadPort;
import harmony.communityservice.guild.guild.application.port.out.ModifyGuildUserNicknamePort;
import harmony.communityservice.guild.guild.application.port.out.ModifyGuildUserNicknamesPort;
import harmony.communityservice.guild.guild.application.port.out.RegisterGuildReadPort;
import harmony.communityservice.guild.guild.domain.Guild.GuildId;
import harmony.communityservice.guild.guild.domain.GuildRead;
import harmony.communityservice.guild.guild.domain.GuildRead.GuildReadId;
import harmony.communityservice.user.application.port.in.LoadUserUseCase;
import harmony.communityservice.user.domain.User;
import harmony.communityservice.user.domain.User.UserId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GuildReadCommandServiceTest {

    @Mock
    LoadUserUseCase loadUserUseCase;
    @Mock
    RegisterGuildReadPort registerGuildReadPort;
    @Mock
    DeleteGuildReadPort deleteGuildReadPort;
    @Mock
    ModifyGuildUserNicknamePort modifyGuildUserNicknamePort;
    @Mock
    ModifyGuildUserNicknamesPort modifyGuildUserNicknamesPort;
    @Mock
    LoadGuildReadPort loadGuildReadPort;
    GuildReadCommandService guildReadCommandService;

    @BeforeEach
    void setting() {
        guildReadCommandService = new GuildReadCommandService(loadUserUseCase, registerGuildReadPort,
                deleteGuildReadPort, modifyGuildUserNicknamePort, modifyGuildUserNicknamesPort, loadGuildReadPort);
    }

    @Test
    @DisplayName("guildRead 등록 테스트")
    void register_guild_read() {
        assertNotNull(guildReadCommandService);

        User user = User.builder()
                .userId(1L)
                .nickname("0chord")
                .email("seaweed.0chord@gmail.com")
                .profile("https://storage.googleapis.com/sg-dev-remember-harmony/discord.png")
                .build();
        GuildRead guildRead = GuildRead.builder()
                .userProfile("http://user.com/test1")
                .userNickname("0chord")
                .guildId(GuildId.make(1L))
                .guildReadId(GuildReadId.make(Threshold.MIN.getValue()))
                .profile("http://guild.com/test1")
                .name("first_guild")
                .userId(UserId.make(1L))
                .build();
        RegisterGuildReadCommand registerGuildReadCommand = RegisterGuildReadCommand.builder()
                .guildId(1L)
                .userId(1L)
                .profile("http://guild.com/test1")
                .name("first_guild")
                .build();

        given(loadUserUseCase.loadUser(1L)).willReturn(user);
        willDoNothing().given(registerGuildReadPort).register(guildRead);

        guildReadCommandService.register(registerGuildReadCommand);
        then(loadUserUseCase).should(times(1)).loadUser(1L);
        then(registerGuildReadPort).should(times(1)).register(guildRead);
    }

    @Test
    @DisplayName("guildRead 삭제 테스트")
    void delete_guild_read() {
        assertNotNull(guildReadCommandService);

        willDoNothing().given(deleteGuildReadPort).delete(GuildId.make(1L));

        guildReadCommandService.delete(1L);

        then(deleteGuildReadPort).should(times(1)).delete(GuildId.make(1L));
    }

    @Test
    @DisplayName("guildRead 조회 테스트")
    void load_guild_read() {
        assertNotNull(guildReadCommandService);

        GuildRead guildRead = GuildRead.builder()
                .userProfile("http://user.com/test1")
                .userNickname("0chord")
                .guildId(GuildId.make(1L))
                .guildReadId(GuildReadId.make(Threshold.MIN.getValue()))
                .profile("http://guild.com/test1")
                .name("first_guild")
                .userId(UserId.make(1L))
                .build();
        given(loadGuildReadPort.loadByUserIdAndGuildId(UserId.make(1L), GuildId.make(1L))).willReturn(guildRead);

        GuildRead resultGuildRead = guildReadCommandService.loadByUserIdAndGuildId(1L, 1L);

        assertEquals(guildRead, resultGuildRead);
        then(loadGuildReadPort).should(times(1)).loadByUserIdAndGuildId(UserId.make(1L), GuildId.make(1L));
    }

    @Test
    @DisplayName("guildRead 유저 nickname 수정 테스트")
    void modify_guild_read() {
        assertNotNull(guildReadCommandService);

        willDoNothing().given(modifyGuildUserNicknamePort).modifyNickname(GuildId.make(1L),
                UserId.make(1L), "0Chord");

        guildReadCommandService.modifyNickname(new ModifyGuildNicknameCommand(1L, 1L, "0Chord"));

        then(modifyGuildUserNicknamePort).should(times(1)).modifyNickname(GuildId.make(1L),
                UserId.make(1L), "0Chord");
    }

    @Test
    @DisplayName("guildRead 유저 nicknames 수정 테스트")
    void modify_guild_read_nicknames() {
        assertNotNull(guildReadCommandService);

        willDoNothing().given(modifyGuildUserNicknamesPort).modify(UserId.make(1L), "0Chord");

        guildReadCommandService.modify(1L, "0Chord");

        then(modifyGuildUserNicknamesPort).should(times(1)).modify(UserId.make(1L), "0Chord");
    }
}
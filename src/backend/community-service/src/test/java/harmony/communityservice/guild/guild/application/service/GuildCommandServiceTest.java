package harmony.communityservice.guild.guild.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;

import harmony.communityservice.common.event.Events;
import harmony.communityservice.common.event.dto.inner.DeleteCategoryEvent;
import harmony.communityservice.common.event.dto.inner.DeleteChannelEvent;
import harmony.communityservice.common.event.dto.inner.DeleteGuildReadEvent;
import harmony.communityservice.common.event.dto.inner.RegisterChannelEvent;
import harmony.communityservice.common.event.dto.inner.RegisterGuildReadEvent;
import harmony.communityservice.common.event.dto.produce.GuildCreatedEvent;
import harmony.communityservice.common.event.dto.produce.GuildDeletedEvent;
import harmony.communityservice.common.service.FileConverter;
import harmony.communityservice.domain.Threshold;
import harmony.communityservice.guild.guild.application.port.in.DeleteGuildCommand;
import harmony.communityservice.guild.guild.application.port.in.JoinGuildCommand;
import harmony.communityservice.guild.guild.application.port.in.RegisterGuildCommand;
import harmony.communityservice.guild.guild.application.port.out.DeleteGuildPort;
import harmony.communityservice.guild.guild.application.port.out.RegisterGuildPort;
import harmony.communityservice.guild.guild.domain.Guild;
import harmony.communityservice.guild.guild.domain.Guild.GuildId;
import harmony.communityservice.user.domain.User.UserId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

@ExtendWith(MockitoExtension.class)
class GuildCommandServiceTest {

    @Mock
    FileConverter fileConverter;
    @Mock
    RegisterGuildPort registerGuildPort;
    @Mock
    DeleteGuildPort deleteGuildPort;
    @Mock
    ApplicationEventPublisher publisher;

    GuildCommandService guildCommandService;

    @BeforeEach
    void setting() {
        guildCommandService = new GuildCommandService(fileConverter, registerGuildPort, deleteGuildPort);
        Events.register(publisher);
    }

    @Test
    @DisplayName("길드 등록 테스트")
    void register_guild() {
        assertNotNull(guildCommandService);

        MultipartFile multipartFile = new MockMultipartFile("file", "test", "text/plain", "test content".getBytes());
        Guild guild = Guild.builder()
                .guildId(GuildId.make(Threshold.MIN.getValue()))
                .name("test")
                .profile("http://cdn.com/test")
                .managerId(UserId.make(1L))
                .inviteCode("8c475c80ffe4401385a040344046abce")
                .build();
        given(fileConverter.fileToUrl(multipartFile)).willReturn("http://cdn.com/test");
        given(registerGuildPort.register(guild)).willReturn(1L);
        RegisterGuildReadEvent registerGuildReadEvent = RegisterGuildReadEvent.builder()
                .userId(guild.getManagerId().getId())
                .guildId(guild.getGuildId().getId())
                .profile(guild.getProfileInfo().getProfile())
                .name(guild.getProfileInfo().getName())
                .build();
        GuildCreatedEvent guildCreatedEvent = GuildCreatedEvent.builder()
                .type("CREATE-GUILD")
                .guildId(guild.getGuildId().getId())
                .name(guild.getProfileInfo().getName())
                .profile(guild.getProfileInfo().getProfile())
                .build();

        RegisterGuildCommand registerGuildCommand = new RegisterGuildCommand(multipartFile, 1L, "test");
        Long guildId = guildCommandService.register(registerGuildCommand);

        assertEquals(guild.getGuildId().getId(), guildId);

        then(fileConverter).should(times(1)).fileToUrl(multipartFile);
        then(registerGuildPort).should(times(1)).register(guild);
        then(publisher).should(times(1))
                .publishEvent(new RegisterChannelEvent(guildId, "기본채널", registerGuildCommand.managerId(), 0L, "TEXT"));
        then(publisher).should(times(1)).publishEvent(registerGuildReadEvent);
        then(publisher).should(times(1)).publishEvent(guildCreatedEvent);
    }

    @Test
    @DisplayName("길드 초대 가입 테스트")
    void join_guild() {
        assertNotNull(guildCommandService);

        Guild guild = Guild.builder()
                .guildId(GuildId.make(Threshold.MIN.getValue()))
                .name("test")
                .profile("http://cdn.com/test")
                .managerId(UserId.make(1L))
                .inviteCode("8c475c80ffe4401385a040344046abce")
                .build();

        RegisterGuildReadEvent registerGuildReadEvent = RegisterGuildReadEvent.builder()
                .userId(guild.getManagerId().getId())
                .guildId(guild.getGuildId().getId())
                .profile(guild.getProfileInfo().getProfile())
                .name(guild.getProfileInfo().getName())
                .build();

        GuildCreatedEvent guildCreatedEvent = GuildCreatedEvent.builder()
                .type("CREATE-GUILD")
                .guildId(guild.getGuildId().getId())
                .name(guild.getProfileInfo().getName())
                .profile(guild.getProfileInfo().getProfile())
                .build();

        JoinGuildCommand joinGuildCommand = new JoinGuildCommand(1L, "8c475c80ffe4401385a040344046abce.1.1");
        given(registerGuildPort.join("8c475c80ffe4401385a040344046abce", UserId.make(1L))).willReturn(guild);

        guildCommandService.join(joinGuildCommand);

        then(registerGuildPort).should(times(1)).join("8c475c80ffe4401385a040344046abce", UserId.make(1L));
        then(publisher).should(times(1)).publishEvent(registerGuildReadEvent);
        then(publisher).should(times(1)).publishEvent(guildCreatedEvent);
    }

    @Test
    @DisplayName("길드 삭제 테스트")
    void delete_guild() {
        assertNotNull(guildCommandService);

        willDoNothing().given(deleteGuildPort).delete(GuildId.make(1L), UserId.make(1L));

        GuildDeletedEvent guildDeletedEvent = GuildDeletedEvent.builder()
                .type("DELETE-GUILD")
                .guildId(1L)
                .build();
        DeleteGuildCommand deleteGuildCommand = new DeleteGuildCommand(1L, 1L);
        DeleteGuildReadEvent deleteGuildReadEvent = new DeleteGuildReadEvent(deleteGuildCommand.guildId());
        DeleteCategoryEvent deleteCategoryEvent = new DeleteCategoryEvent(deleteGuildCommand.guildId());
        DeleteChannelEvent deleteChannelEvent = new DeleteChannelEvent(deleteGuildCommand.guildId());
        guildCommandService.delete(deleteGuildCommand);

        then(publisher).should(times(1)).publishEvent(guildDeletedEvent);
        then(publisher).should(times(1)).publishEvent(deleteGuildReadEvent);
        then(publisher).should(times(1)).publishEvent(deleteCategoryEvent);
        then(publisher).should(times(1)).publishEvent(deleteChannelEvent);
    }
}
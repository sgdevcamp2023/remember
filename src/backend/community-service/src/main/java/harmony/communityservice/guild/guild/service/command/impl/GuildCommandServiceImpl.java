package harmony.communityservice.guild.guild.service.command.impl;

import harmony.communityservice.common.annotation.AuthorizeGuildManager;
import harmony.communityservice.common.event.Events;
import harmony.communityservice.common.event.dto.inner.DeleteCategoryEvent;
import harmony.communityservice.common.event.dto.inner.DeleteChannelEvent;
import harmony.communityservice.common.event.dto.inner.DeleteGuildReadEvent;
import harmony.communityservice.common.event.dto.inner.RegisterChannelEvent;
import harmony.communityservice.common.event.dto.inner.RegisterUserReadEvent;
import harmony.communityservice.common.event.mapper.ToRegisterGuildReadEventMapper;
import harmony.communityservice.common.exception.NotFoundDataException;
import harmony.communityservice.common.service.ContentService;
import harmony.communityservice.guild.guild.domain.Guild;
import harmony.communityservice.guild.guild.domain.GuildId;
import harmony.communityservice.guild.guild.domain.GuildUser;
import harmony.communityservice.guild.guild.dto.DeleteGuildRequest;
import harmony.communityservice.guild.guild.dto.RegisterGuildRequest;
import harmony.communityservice.guild.guild.dto.RegisterUserUsingInvitationCodeRequest;
import harmony.communityservice.guild.guild.mapper.ToGuildMapper;
import harmony.communityservice.guild.guild.repository.command.GuildCommandRepository;
import harmony.communityservice.guild.guild.service.command.GuildCommandService;
import harmony.communityservice.user.domain.UserId;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Transactional
@RequiredArgsConstructor
public class GuildCommandServiceImpl implements GuildCommandService {

    private final GuildCommandRepository guildCommandRepository;
    private final ContentService contentService;

    @Override
    public Long register(RegisterGuildRequest registerGuildRequest, MultipartFile profile) {
        Guild guild = createGuild(registerGuildRequest, profile);
        guild.updateUserIds(GuildUser.make(UserId.make(registerGuildRequest.managerId())));
        guildCommandRepository.save(guild);
        registerUserRead(registerGuildRequest.managerId(), guild.getGuildId().getId());
        Events.send(new RegisterChannelEvent(
                guild.getGuildId().getId(), "기본채널", registerGuildRequest.getUserId(), 0L, "TEXT"));
        registerGuildRead(registerGuildRequest.managerId(), guild);
        return guild.getGuildId().getId();
    }

    @Override
    public void joinByInvitationCode(RegisterUserUsingInvitationCodeRequest registerUserUsingInvitationCodeRequest) {
        List<String> parsedInvitationCodes = List.of(
                registerUserUsingInvitationCodeRequest.invitationCode().split("\\."));
        Guild targetGuild = guildCommandRepository.findByInvitationCode(parsedInvitationCodes.get(0))
                .orElseThrow(NotFoundDataException::new);
        targetGuild.updateUserIds(GuildUser.make(UserId.make(registerUserUsingInvitationCodeRequest.userId())));
        guildCommandRepository.save(targetGuild);
        registerGuildRead(registerUserUsingInvitationCodeRequest.userId(), targetGuild);
        registerUserRead(registerUserUsingInvitationCodeRequest.userId(), targetGuild.getGuildId().getId());
    }

    private Guild createGuild(RegisterGuildRequest registerGuildRequest, MultipartFile profile) {
        String uploadedImageUrl = contentService.convertFileToUrl(profile);
        return ToGuildMapper.convert(registerGuildRequest, uploadedImageUrl);
    }

    private void registerUserRead(Long userId, Long guildId) {
        Events.send(new RegisterUserReadEvent(guildId, userId));
    }

    private void registerGuildRead(Long userId, Guild guild) {
        Events.send(ToRegisterGuildReadEventMapper.convert(guild, userId));
    }

    @Override
    @AuthorizeGuildManager
    public void delete(DeleteGuildRequest deleteGuildRequest) {
        guildCommandRepository.deleteById(GuildId.make(deleteGuildRequest.guildId()));
        Events.send(new DeleteGuildReadEvent(deleteGuildRequest.guildId()));
        Events.send(new DeleteCategoryEvent(deleteGuildRequest.guildId()));
        Events.send(new DeleteChannelEvent(deleteGuildRequest.guildId()));
    }
}

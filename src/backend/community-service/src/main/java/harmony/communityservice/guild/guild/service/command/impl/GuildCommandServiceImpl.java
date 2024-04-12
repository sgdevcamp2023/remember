package harmony.communityservice.guild.guild.service.command.impl;

import harmony.communityservice.common.annotation.AuthorizeGuildManager;
import harmony.communityservice.common.exception.NotFoundDataException;
import harmony.communityservice.common.service.ContentService;
import harmony.communityservice.guild.category.service.command.CategoryCommandService;
import harmony.communityservice.guild.channel.dto.RegisterChannelRequest;
import harmony.communityservice.guild.channel.service.command.ChannelCommandService;
import harmony.communityservice.guild.domain.Guild;
import harmony.communityservice.guild.domain.GuildRead;
import harmony.communityservice.guild.guild.dto.DeleteGuildRequest;
import harmony.communityservice.guild.guild.dto.RegisterGuildReadRequest;
import harmony.communityservice.guild.guild.dto.RegisterGuildRequest;
import harmony.communityservice.guild.guild.dto.RegisterUserUsingInvitationCodeRequest;
import harmony.communityservice.guild.guild.dto.ToSearchGuildReadRequestMapper;
import harmony.communityservice.guild.guild.mapper.ToGuildMapper;
import harmony.communityservice.guild.guild.repository.command.GuildCommandRepository;
import harmony.communityservice.guild.guild.service.command.GuildCommandService;
import harmony.communityservice.guild.guild.service.command.GuildReadCommandService;
import harmony.communityservice.user.dto.RegisterUserReadRequest;
import harmony.communityservice.user.service.command.UserReadCommandService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Transactional
@RequiredArgsConstructor
public class GuildCommandServiceImpl implements GuildCommandService {

    private final GuildCommandRepository guildCommandRepository;
    private final GuildReadCommandService guildReadCommandService;
    private final UserReadCommandService userReadCommandService;
    private final CategoryCommandService categoryCommandService;
    private final ChannelCommandService channelCommandService;
    private final ContentService contentService;

    @Override
    public GuildRead register(RegisterGuildRequest registerGuildRequest, MultipartFile profile) {
        Guild guild = createGuild(registerGuildRequest, profile);
        guild.updateUserIds(registerGuildRequest.managerId());
        guildCommandRepository.save(guild);
        registerUserRead(registerGuildRequest.managerId(), guild.getGuildId());
        registerFirstChannel(registerGuildRequest.managerId(), guild);
        return registerGuildRead(registerGuildRequest.managerId(), guild);
    }

    @Override
    public void joinByInvitationCode(RegisterUserUsingInvitationCodeRequest registerUserUsingInvitationCodeRequest) {
        List<String> parsedInvitationCodes = List.of(
                registerUserUsingInvitationCodeRequest.invitationCode().split("\\."));
        Guild targetGuild = guildCommandRepository.findByInvitationCode(parsedInvitationCodes.get(0))
                .orElseThrow(NotFoundDataException::new);
        targetGuild.updateUserIds(registerUserUsingInvitationCodeRequest.userId());
        guildCommandRepository.save(targetGuild);
        registerGuildRead(registerUserUsingInvitationCodeRequest.userId(), targetGuild);
        registerUserRead(registerUserUsingInvitationCodeRequest.userId(), targetGuild.getGuildId());
    }

    private Guild createGuild(RegisterGuildRequest registerGuildRequest, MultipartFile profile) {
        String uploadedImageUrl = contentService.convertFileToUrl(profile);
        return ToGuildMapper.convert(registerGuildRequest, uploadedImageUrl);
    }

    private void registerUserRead(Long userId, Long guildId) {
        userReadCommandService.register(new RegisterUserReadRequest(userId, guildId));
    }

    private void registerFirstChannel(Long userId, Guild targetGuild) {
        RegisterChannelRequest registerChannelRequest = new RegisterChannelRequest(
                targetGuild.getGuildId(), "기본채널", userId, 0L, "TEXT");
        channelCommandService.register(registerChannelRequest);
        guildCommandRepository.save(targetGuild);
    }


    private GuildRead registerGuildRead(Long userId, Guild guild) {
        RegisterGuildReadRequest registerGuildReadRequest = ToSearchGuildReadRequestMapper.convert(guild,
                userId);
        return guildReadCommandService.register(registerGuildReadRequest);
    }

    @Override
    @AuthorizeGuildManager
    public void delete(DeleteGuildRequest deleteGuildRequest) {
        guildCommandRepository.deleteById(deleteGuildRequest.guildId());
        guildReadCommandService.delete(deleteGuildRequest.guildId());
        categoryCommandService.deleteByGuildId(deleteGuildRequest.guildId());
        channelCommandService.deleteByGuildId(deleteGuildRequest.guildId());
    }
}

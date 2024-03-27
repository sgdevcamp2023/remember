package harmony.communityservice.community.command.service.impl;

import harmony.communityservice.common.service.ContentService;
import harmony.communityservice.community.command.dto.DeleteGuildRequest;
import harmony.communityservice.community.command.dto.ModifyUserNicknameInGuildRequest;
import harmony.communityservice.community.command.dto.RegisterChannelRequest;
import harmony.communityservice.community.command.dto.RegisterGuildReadRequest;
import harmony.communityservice.community.command.dto.RegisterGuildRequest;
import harmony.communityservice.community.command.dto.RegisterUserReadRequest;
import harmony.communityservice.community.command.repository.GuildCommandRepository;
import harmony.communityservice.community.command.service.ChannelCommandService;
import harmony.communityservice.community.command.service.GuildCommandService;
import harmony.communityservice.community.command.service.GuildReadCommandService;
import harmony.communityservice.community.command.service.GuildUserCommandService;
import harmony.communityservice.community.command.service.UserReadCommandService;
import harmony.communityservice.community.domain.Guild;
import harmony.communityservice.community.domain.GuildRead;
import harmony.communityservice.community.domain.User;
import harmony.communityservice.community.domain.UserRead;
import harmony.communityservice.community.mapper.ToGuildMapper;
import harmony.communityservice.community.mapper.ToRegisterUserReadRequestMapper;
import harmony.communityservice.community.mapper.ToSearchGuildReadRequestMapper;
import harmony.communityservice.community.query.service.GuildQueryService;
import harmony.communityservice.community.query.service.UserQueryService;
import harmony.communityservice.community.query.service.UserReadQueryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
public class GuildCommandServiceImpl implements GuildCommandService {

    private final GuildCommandRepository guildCommandRepository;
    private final GuildReadCommandService guildReadCommandService;
    private final UserQueryService userQueryService;
    private final GuildUserCommandService guildUserCommandService;
    private final UserReadCommandService userReadCommandService;
    private final GuildQueryService guildQueryService;
    private final UserReadQueryService userReadQueryService;
    private final ContentService contentService;
    private final ChannelCommandService channelCommandService;

    @Override
    public GuildRead register(RegisterGuildRequest registerGuildRequest, MultipartFile profile) {
        String uploadedImageUrl = contentService.convertFileToUrl(profile);
        Guild guild = ToGuildMapper.convert(registerGuildRequest, uploadedImageUrl);
        guildCommandRepository.save(guild);
        RegisterGuildReadRequest registerGuildReadRequest = ToSearchGuildReadRequestMapper.convert(guild,
                registerGuildRequest.getManagerId());
        GuildRead guildRead = guildReadCommandService.register(registerGuildReadRequest);
        User targetUser = userQueryService.searchByUserId(registerGuildRequest.getManagerId());
        guildUserCommandService.register(guild, targetUser);
        RegisterUserReadRequest registerUserReadRequest = ToRegisterUserReadRequestMapper.convert(guild, targetUser);
        userReadCommandService.register(registerUserReadRequest);
        RegisterChannelRequest registerChannelRequest = new RegisterChannelRequest(
                guild.getGuildId(), "기본채널", registerGuildRequest.getManagerId(), 0L, "TEXT");
        channelCommandService.register(registerChannelRequest);
        return guildRead;
    }

    @Override
    public void joinByInvitationCode(String invitationCode, Long userId) {
        List<String> parsedInvitationCodes = List.of(invitationCode.split("\\."));
        Guild targetGuild = guildQueryService.searchByInvitationCode(parsedInvitationCodes.get(0));
        RegisterGuildReadRequest registerGuildReadRequest = ToSearchGuildReadRequestMapper.convert(targetGuild, userId);
        guildReadCommandService.register(registerGuildReadRequest);
        User targetUser = userQueryService.searchByUserId(userId);
        guildUserCommandService.register(targetGuild, targetUser);
        RegisterUserReadRequest registerUserReadRequest = ToRegisterUserReadRequestMapper.convert(targetGuild,
                targetUser);
        userReadCommandService.register(registerUserReadRequest);
    }

    @Override
    public void delete(DeleteGuildRequest deleteGuildRequest) {
        guildQueryService.existsByGuildIdAndManagerId(deleteGuildRequest.getGuildId(),
                deleteGuildRequest.getManagerId());
        guildCommandRepository.delete(deleteGuildRequest.getGuildId());
        guildReadCommandService.delete(deleteGuildRequest.getGuildId());
    }

    @Override
    public void modifyUserNicknameInGuild(ModifyUserNicknameInGuildRequest modifyUserNicknameInGuildRequest) {
        UserRead targetUserRead = userReadQueryService.searchByUserIdAndGuildId(
                modifyUserNicknameInGuildRequest.getUserId(),
                modifyUserNicknameInGuildRequest.getGuildId());
        targetUserRead.modifyNickname(modifyUserNicknameInGuildRequest.getNickname());
    }
}

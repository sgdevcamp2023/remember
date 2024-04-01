package harmony.communityservice.community.command.service.impl;

import harmony.communityservice.common.dto.SearchUserReadRequest;
import harmony.communityservice.common.service.ContentService;
import harmony.communityservice.community.command.dto.DeleteGuildRequest;
import harmony.communityservice.community.command.dto.ModifyUserNicknameInGuildRequest;
import harmony.communityservice.community.command.dto.RegisterChannelRequest;
import harmony.communityservice.community.command.dto.RegisterGuildReadRequest;
import harmony.communityservice.community.command.dto.RegisterGuildRequest;
import harmony.communityservice.community.command.dto.RegisterUserReadRequest;
import harmony.communityservice.community.command.dto.RegisterUserUsingInvitationCodeRequest;
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
        Guild guild = createGuild(registerGuildRequest, profile);
        guildCommandRepository.save(guild);
        registerGuildUserAndUserRead(registerGuildRequest.managerId(), guild);
        registerChannel(registerGuildRequest, guild);
        return registerGuildRead(registerGuildRequest.managerId(), guild);
    }

    @Override
    public void joinByInvitationCode(RegisterUserUsingInvitationCodeRequest registerUserUsingInvitationCodeRequest) {
        List<String> parsedInvitationCodes = List.of(registerUserUsingInvitationCodeRequest.invitationCode().split("\\."));
        Guild targetGuild = guildQueryService.searchByInvitationCode(parsedInvitationCodes.get(0));
        registerGuildRead(registerUserUsingInvitationCodeRequest.userId(), targetGuild);
        registerGuildUserAndUserRead(registerUserUsingInvitationCodeRequest.userId(), targetGuild);
    }

    private void registerChannel(RegisterGuildRequest registerGuildRequest, Guild guild) {
        RegisterChannelRequest registerChannelRequest = new RegisterChannelRequest(
                guild.getGuildId(), "기본채널", registerGuildRequest.managerId(), 0L, "TEXT");
        channelCommandService.register(registerChannelRequest);
    }

    private void registerGuildUserAndUserRead(Long userId, Guild guild) {
        User targetUser = userQueryService.searchByUserId(userId);
        guildUserCommandService.register(guild, targetUser);
        RegisterUserReadRequest registerUserReadRequest = ToRegisterUserReadRequestMapper.convert(guild, targetUser);
        userReadCommandService.register(registerUserReadRequest);
    }

    private GuildRead registerGuildRead(Long userId, Guild guild) {
        RegisterGuildReadRequest registerGuildReadRequest = ToSearchGuildReadRequestMapper.convert(guild,
                userId);
        return guildReadCommandService.register(registerGuildReadRequest);
    }

    private Guild createGuild(RegisterGuildRequest registerGuildRequest, MultipartFile profile) {
        String uploadedImageUrl = contentService.convertFileToUrl(profile);
        return ToGuildMapper.convert(registerGuildRequest, uploadedImageUrl);
    }


    @Override
    public void delete(DeleteGuildRequest deleteGuildRequest) {
        guildQueryService.existsByGuildIdAndManagerId(deleteGuildRequest.guildId(),
                deleteGuildRequest.managerId());
        guildCommandRepository.delete(deleteGuildRequest.guildId());
        guildReadCommandService.delete(deleteGuildRequest.guildId());
    }

    @Override
    public void modifyUserNicknameInGuild(ModifyUserNicknameInGuildRequest modifyUserNicknameInGuildRequest) {
        UserRead targetUserRead = userReadQueryService.searchByUserIdAndGuildId(
                new SearchUserReadRequest(modifyUserNicknameInGuildRequest.userId(),
                        modifyUserNicknameInGuildRequest.guildId()));
        targetUserRead.modifyNickname(modifyUserNicknameInGuildRequest.nickname());
    }
}

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
import harmony.communityservice.community.command.service.UserReadCommandService;
import harmony.communityservice.community.domain.Guild;
import harmony.communityservice.community.domain.GuildRead;
import harmony.communityservice.community.domain.UserRead;
import harmony.communityservice.community.mapper.ToGuildMapper;
import harmony.communityservice.community.mapper.ToSearchGuildReadRequestMapper;
import harmony.communityservice.community.query.service.GuildQueryService;
import harmony.communityservice.community.query.service.UserReadQueryService;
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
    private final GuildQueryService guildQueryService;
    private final UserReadQueryService userReadQueryService;
    private final ContentService contentService;
    private final ChannelCommandService channelCommandService;

    @Override
    public GuildRead register(RegisterGuildRequest registerGuildRequest, MultipartFile profile) {
        Guild guild = createGuild(registerGuildRequest, profile);
        guildCommandRepository.save(guild);
        registerUserRead(registerGuildRequest.managerId(), guild.getGuildId());
        registerChannel(registerGuildRequest.managerId(), guild.getGuildId());
        return registerGuildRead(registerGuildRequest.managerId(), guild);
    }

    @Override
    public void joinByInvitationCode(RegisterUserUsingInvitationCodeRequest registerUserUsingInvitationCodeRequest) {
        List<String> parsedInvitationCodes = List.of(
                registerUserUsingInvitationCodeRequest.invitationCode().split("\\."));
        Guild targetGuild = guildQueryService.searchByInvitationCode(parsedInvitationCodes.get(0));
        registerGuildRead(registerUserUsingInvitationCodeRequest.userId(), targetGuild);
        registerUserRead(registerUserUsingInvitationCodeRequest.userId(), targetGuild.getGuildId());
    }

    private Guild createGuild(RegisterGuildRequest registerGuildRequest, MultipartFile profile) {
        String uploadedImageUrl = contentService.convertFileToUrl(profile);
        return ToGuildMapper.convert(registerGuildRequest, uploadedImageUrl, registerGuildRequest.managerId());
    }

    private void registerUserRead(Long userId, Long guildId) {
        userReadCommandService.register(new RegisterUserReadRequest(userId, guildId));
    }

    private void registerChannel(Long userId, Long guildId) {
        RegisterChannelRequest registerChannelRequest = new RegisterChannelRequest(
                guildId, "기본채널", userId, 0L, "TEXT");
        channelCommandService.register(registerChannelRequest);
    }


    private GuildRead registerGuildRead(Long userId, Guild guild) {
        RegisterGuildReadRequest registerGuildReadRequest = ToSearchGuildReadRequestMapper.convert(guild,
                userId);
        return guildReadCommandService.register(registerGuildReadRequest);
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

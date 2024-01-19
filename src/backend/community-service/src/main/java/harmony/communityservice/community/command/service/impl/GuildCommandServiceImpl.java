package harmony.communityservice.community.command.service.impl;

import harmony.communityservice.common.service.ContentService;
import harmony.communityservice.community.command.dto.GuildDeleteRequestDto;
import harmony.communityservice.community.command.dto.GuildReadRequestDto;
import harmony.communityservice.community.command.dto.GuildRegistrationRequestDto;
import harmony.communityservice.community.command.dto.GuildUpdateNicknameRequestDto;
import harmony.communityservice.community.command.dto.UserReadRequestDto;
import harmony.communityservice.community.command.repository.GuildCommandRepository;
import harmony.communityservice.community.command.service.GuildCommandService;
import harmony.communityservice.community.command.service.GuildReadCommandService;
import harmony.communityservice.community.command.service.GuildUserCommandService;
import harmony.communityservice.community.command.service.UserReadCommandService;
import harmony.communityservice.community.domain.Guild;
import harmony.communityservice.community.domain.User;
import harmony.communityservice.community.domain.UserRead;
import harmony.communityservice.community.mapper.ToGuildMapper;
import harmony.communityservice.community.mapper.ToGuildReadRequestDtoMapper;
import harmony.communityservice.community.mapper.ToUserReadRequestDtoMapper;
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
    @Override
    public void save(GuildRegistrationRequestDto requestDto, MultipartFile profile) {
        String imageUrl = contentService.imageConvertUrl(profile);
        Guild guild = ToGuildMapper.convert(requestDto, imageUrl);
        guildCommandRepository.save(guild);
        GuildReadRequestDto guildReadRequestDto = ToGuildReadRequestDtoMapper.convert(guild, requestDto.getManagerId());
        guildReadCommandService.save(guildReadRequestDto);
        User findUser = userQueryService.findUser(requestDto.getManagerId());
        guildUserCommandService.save(guild, findUser);
        UserReadRequestDto userReadRequestDto = ToUserReadRequestDtoMapper.convert(guild, findUser);
        userReadCommandService.save(userReadRequestDto);
    }

    @Override
    public void join(String invitationCode, Long userId) {
        List<String> splitCodes = List.of(invitationCode.split("\\."));
        Guild findGuild = guildQueryService.findGuildByInviteCode(splitCodes.get(0));
        GuildReadRequestDto guildReadRequestDto = ToGuildReadRequestDtoMapper.convert(findGuild, userId);
        guildReadCommandService.save(guildReadRequestDto);
        User findUser = userQueryService.findUser(userId);
        guildUserCommandService.save(findGuild, findUser);
        UserReadRequestDto userReadRequestDto = ToUserReadRequestDtoMapper.convert(findGuild, findUser);
        userReadCommandService.save(userReadRequestDto);
    }

    @Override
    public void remove(GuildDeleteRequestDto guildDeleteRequestDto) {
        guildQueryService.existsGuildByGuildIdAndManagerId(guildDeleteRequestDto.getGuildId(),
                guildDeleteRequestDto.getManagerId());
        guildCommandRepository.delete(guildDeleteRequestDto.getGuildId());
    }

    @Override
    public void updateGuildNickname(GuildUpdateNicknameRequestDto requestDto) {
        UserRead findUserRead = userReadQueryService.findUserReadIdAndGuildId(requestDto.getUserId(),
                requestDto.getGuildId());
        findUserRead.updateNickname(requestDto.getNickname());
    }
}

package harmony.communityservice.community.command.service.impl;

import harmony.communityservice.community.command.dto.GuildDeleteRequestDto;
import harmony.communityservice.community.command.dto.GuildReadRequestDto;
import harmony.communityservice.community.command.dto.GuildRegistrationRequestDto;
import harmony.communityservice.community.command.dto.UserReadRequestDto;
import harmony.communityservice.community.command.repository.GuildCommandRepository;
import harmony.communityservice.community.command.service.GuildCommandService;
import harmony.communityservice.community.command.service.GuildReadCommandService;
import harmony.communityservice.community.command.service.GuildUserCommandService;
import harmony.communityservice.community.command.service.UserReadCommandService;
import harmony.communityservice.community.domain.Guild;
import harmony.communityservice.community.domain.User;
import harmony.communityservice.community.mapper.ToGuildMapper;
import harmony.communityservice.community.mapper.ToGuildReadRequestDtoMapper;
import harmony.communityservice.community.mapper.ToUserReadRequestDtoMapper;
import harmony.communityservice.community.query.service.GuildQueryService;
import harmony.communityservice.community.query.service.UserQueryService;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GuildCommandServiceImpl implements GuildCommandService {

    private final GuildCommandRepository guildCommandRepository;
    private final GuildReadCommandService guildReadCommandService;
    private final UserQueryService userQueryService;
    private final GuildUserCommandService guildUserCommandService;
    private final UserReadCommandService userReadCommandService;
    private final GuildQueryService guildQueryService;

    @Override
    public void save(GuildRegistrationRequestDto requestDto, String profile) {
        Guild guild = ToGuildMapper.convert(requestDto, profile);
        guildCommandRepository.save(guild);
        GuildReadRequestDto guildReadRequestDto = ToGuildReadRequestDtoMapper.convert(guild);
        guildReadCommandService.save(guildReadRequestDto);
        User findUser = userQueryService.findUser(requestDto.getManagerId());
        guildUserCommandService.save(guild, findUser);
        UserReadRequestDto userReadRequestDto = ToUserReadRequestDtoMapper.convert(guild, findUser);
        userReadCommandService.save(userReadRequestDto);
    }

    @Override
    public void join(String invitationCode) {
        List<String> splitCodes = List.of(invitationCode.split("."));
        long userId = Long.parseLong(splitCodes.get(1));
        Guild findGuild = guildQueryService.findGuildByInviteCode(splitCodes.get(0));
        GuildReadRequestDto guildReadRequestDto = ToGuildReadRequestDtoMapper.convert(findGuild);
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
}

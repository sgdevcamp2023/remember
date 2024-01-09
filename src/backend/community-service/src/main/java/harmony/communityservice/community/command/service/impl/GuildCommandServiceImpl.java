package harmony.communityservice.community.command.service.impl;

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
import harmony.communityservice.community.query.service.UserQueryService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GuildCommandServiceImpl implements GuildCommandService {

    private final GuildCommandRepository guildCommandRepository;
    private final GuildReadCommandService guildReadCommandService;
    private final UserQueryService userQueryService;
    private final GuildUserCommandService guildUserCommandService;
    private final UserReadCommandService userReadCommandService;

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
}

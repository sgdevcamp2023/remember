package harmony.communityservice.community.command.service.impl;

import harmony.communityservice.community.command.dto.GuildReadRequestDto;
import harmony.communityservice.community.command.repository.GuildReadCommandRepository;
import harmony.communityservice.community.command.service.GuildReadCommandService;
import harmony.communityservice.community.domain.GuildRead;
import harmony.communityservice.community.mapper.ToGuildReadMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GuildReadCommandServiceImpl implements GuildReadCommandService {

    private final GuildReadCommandRepository repository;

    @Override
    public void save(GuildReadRequestDto requestDto) {
        GuildRead guildRead = ToGuildReadMapper.convert(requestDto);
        repository.save(guildRead);
    }
}

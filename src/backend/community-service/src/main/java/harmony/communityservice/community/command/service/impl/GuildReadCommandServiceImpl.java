package harmony.communityservice.community.command.service.impl;

import harmony.communityservice.common.service.ProducerService;
import harmony.communityservice.community.command.dto.GuildReadRequestDto;
import harmony.communityservice.community.command.repository.GuildReadCommandRepository;
import harmony.communityservice.community.command.service.GuildReadCommandService;
import harmony.communityservice.community.domain.GuildRead;
import harmony.communityservice.community.mapper.ToGuildReadMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GuildReadCommandServiceImpl implements GuildReadCommandService {

    private final GuildReadCommandRepository repository;
    private final ProducerService producerService;

    @Override
    public GuildRead save(GuildReadRequestDto requestDto) {
        GuildRead guildRead = ToGuildReadMapper.convert(requestDto);
        repository.save(guildRead);
//        producerService.sendCreateGuild(guildRead);
        return guildRead;
    }

    @Override
    public void delete(long guildId) {
        repository.delete(guildId);
    }
}

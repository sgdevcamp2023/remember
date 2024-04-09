package harmony.communityservice.guild.guild.service.command.impl;

import harmony.communityservice.guild.guild.dto.RegisterGuildReadRequest;
import harmony.communityservice.guild.guild.repository.command.GuildReadCommandRepository;
import harmony.communityservice.guild.guild.service.command.GuildReadCommandService;
import harmony.communityservice.common.service.ProducerService;
import harmony.communityservice.guild.domain.GuildRead;
import harmony.communityservice.guild.guild.mapper.ToGuildReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
public class GuildReadCommandServiceImpl implements GuildReadCommandService {

    private final GuildReadCommandRepository repository;
    private final ProducerService producerService;

    @Override
    public GuildRead register(RegisterGuildReadRequest registerGuildReadRequest) {
        GuildRead guildRead = ToGuildReadMapper.convert(registerGuildReadRequest);
        repository.save(guildRead);
        producerService.publishGuildCreationEvent(guildRead);
        return guildRead;
    }

    @Override
    public void delete(long guildId) {
        repository.delete(guildId);
    }
}

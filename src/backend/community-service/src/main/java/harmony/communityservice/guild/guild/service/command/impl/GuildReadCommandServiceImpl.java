package harmony.communityservice.guild.guild.service.command.impl;

import harmony.communityservice.common.event.Events;
import harmony.communityservice.common.event.dto.GuildCreatedEvent;
import harmony.communityservice.common.event.mapper.ToGuildCreatedEventMapper;
import harmony.communityservice.guild.guild.domain.GuildRead;
import harmony.communityservice.guild.guild.dto.RegisterGuildReadRequest;
import harmony.communityservice.guild.guild.mapper.ToGuildReadMapper;
import harmony.communityservice.guild.guild.repository.command.GuildReadCommandRepository;
import harmony.communityservice.guild.guild.service.command.GuildReadCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
public class GuildReadCommandServiceImpl implements GuildReadCommandService {

    private final GuildReadCommandRepository repository;

    @Override
    public GuildRead register(RegisterGuildReadRequest registerGuildReadRequest) {
        GuildRead guildRead = ToGuildReadMapper.convert(registerGuildReadRequest);
        repository.save(guildRead);
        GuildCreatedEvent event = ToGuildCreatedEventMapper.convert(guildRead);
        Events.send(event);
        return guildRead;
    }

    @Override
    public void delete(long guildId) {
        repository.delete(guildId);
    }
}

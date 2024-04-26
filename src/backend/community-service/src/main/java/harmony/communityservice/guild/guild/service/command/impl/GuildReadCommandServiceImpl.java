package harmony.communityservice.guild.guild.service.command.impl;

import harmony.communityservice.guild.guild.domain.GuildId;
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
    public void register(RegisterGuildReadRequest registerGuildReadRequest) {
        GuildRead guildRead = ToGuildReadMapper.convert(registerGuildReadRequest);
        repository.save(guildRead);
    }

    @Override
    public void delete(long guildId) {
        repository.delete(GuildId.make(guildId));
    }
}

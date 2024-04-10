package harmony.communityservice.guild.channel.service.command.impl;

import harmony.communityservice.common.annotation.AuthorizeGuildMember;
import harmony.communityservice.common.exception.NotFoundDataException;
import harmony.communityservice.guild.channel.dto.DeleteChannelRequest;
import harmony.communityservice.guild.channel.dto.RegisterChannelRequest;
import harmony.communityservice.guild.channel.mapper.ToChannelMapper;
import harmony.communityservice.guild.channel.service.command.ChannelCommandService;
import harmony.communityservice.guild.domain.Channel;
import harmony.communityservice.guild.domain.Guild;
import harmony.communityservice.guild.guild.repository.command.GuildCommandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
public class ChannelCommandServiceImpl implements ChannelCommandService {

    private final GuildCommandRepository guildCommandRepository;

    @Override
    @AuthorizeGuildMember
    public Long registerChannel(RegisterChannelRequest registerChannelRequest) {
        Channel channel = ToChannelMapper.convert(registerChannelRequest);
        Guild targetGuild = guildCommandRepository.findById(registerChannelRequest.guildId())
                .orElseThrow(NotFoundDataException::new);
        targetGuild.registerChannel(channel);
        guildCommandRepository.save(targetGuild);
        return channel.getChannelId();
    }

    @Override
    @AuthorizeGuildMember
    public void delete(DeleteChannelRequest deleteChannelRequest) {
        Guild targetGuild = guildCommandRepository.findById(deleteChannelRequest.guildId())
                .orElseThrow(NotFoundDataException::new);
        targetGuild.deleteChannel(deleteChannelRequest.channelId());
        guildCommandRepository.save(targetGuild);
    }
}

package harmony.communityservice.guild.channel.service.command.impl;

import harmony.communityservice.common.annotation.AuthorizeGuildMember;
import harmony.communityservice.guild.channel.dto.DeleteChannelRequest;
import harmony.communityservice.guild.channel.dto.RegisterChannelRequest;
import harmony.communityservice.guild.channel.mapper.ToChannelMapper;
import harmony.communityservice.guild.channel.service.command.ChannelCommandService;
import harmony.communityservice.guild.domain.Channel;
import harmony.communityservice.guild.domain.Guild;
import harmony.communityservice.guild.guild.service.query.GuildQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
public class ChannelCommandServiceImpl implements ChannelCommandService {

    private final GuildQueryService guildQueryService;

    @Override
    @AuthorizeGuildMember
    public Integer register(RegisterChannelRequest registerChannelRequest) {
        Channel channel = ToChannelMapper.convert(registerChannelRequest);
        Guild targetGuild = guildQueryService.searchById(registerChannelRequest.guildId());
        return targetGuild.registerChannel(channel);
    }

    @Override
    @AuthorizeGuildMember
    public void delete(DeleteChannelRequest deleteChannelRequest) {
        Guild targetGuild = guildQueryService.searchById(deleteChannelRequest.guildId());
        targetGuild.deleteChannel(deleteChannelRequest.channelId());
    }
}

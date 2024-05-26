package harmony.communityservice.guild.channel.application.service;

import harmony.communityservice.domain.Threshold;
import harmony.communityservice.guild.category.domain.Category.CategoryId;
import harmony.communityservice.guild.channel.application.port.in.RegisterChannelCommand;
import harmony.communityservice.guild.channel.domain.Channel;
import harmony.communityservice.guild.channel.domain.Channel.ChannelId;
import harmony.communityservice.guild.guild.domain.Guild.GuildId;

class ChannelMapper {

    static Channel convert(RegisterChannelCommand registerChannelCommand) {
        return Channel.builder()
                .channelId(ChannelId.make(Threshold.MIN.getValue()))
                .name(registerChannelCommand.name())
                .type(registerChannelCommand.type())
                .categoryId(CategoryId.make(registerChannelCommand.categoryId()))
                .guildId(GuildId.make(registerChannelCommand.guildId()))
                .build();
    }
}

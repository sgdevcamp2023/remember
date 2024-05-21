package harmony.communityservice.guild.channel.adapter.out.persistence;

import harmony.communityservice.guild.category.domain.Category.CategoryId;
import harmony.communityservice.guild.channel.domain.Channel;
import harmony.communityservice.guild.channel.domain.Channel.ChannelId;
import harmony.communityservice.guild.guild.domain.Guild.GuildId;

class ChannelMapper {

    static Channel convert(ChannelEntity channelEntity) {
        return Channel.builder()
                .channelId(ChannelId.make(channelEntity.getId().getId()))
                .type(channelEntity.getChannelType().name())
                .name(channelEntity.getName())
                .categoryId(CategoryId.make(channelEntity.getCategoryId().getId()))
                .guildId(GuildId.make(channelEntity.getGuildId().getId()))
                .build();
    }
}

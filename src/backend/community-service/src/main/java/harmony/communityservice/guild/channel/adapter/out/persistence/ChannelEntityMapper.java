package harmony.communityservice.guild.channel.adapter.out.persistence;

import harmony.communityservice.guild.category.adapter.out.persistence.CategoryIdJpaVO;
import harmony.communityservice.guild.channel.domain.Channel;
import harmony.communityservice.guild.guild.adapter.out.persistence.GuildIdJpaVO;

class ChannelEntityMapper {

    static ChannelEntity convert(Channel channel) {
        return ChannelEntity.builder()
                .type(channel.getType().name())
                .guildId(GuildIdJpaVO.make(channel.getGuildId().getId()))
                .categoryId(CategoryIdJpaVO.make(channel.getCategoryId().getId()))
                .name(channel.getName())
                .build();
    }
}

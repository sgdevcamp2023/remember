package harmony.communityservice.guild.channel.mapper;

import harmony.communityservice.guild.category.adapter.out.persistence.CategoryIdJpaVO;
import harmony.communityservice.guild.channel.dto.RegisterChannelRequest;
import harmony.communityservice.guild.channel.adapter.out.persistence.ChannelEntity;
import harmony.communityservice.guild.guild.adapter.out.persistence.GuildIdJpaVO;

public class ToChannelMapper {

    public static ChannelEntity convert(RegisterChannelRequest registerChannelRequest) {
        return ChannelEntity.builder()
                .name(registerChannelRequest.name())
                .type(registerChannelRequest.type())
                .categoryId(CategoryIdJpaVO.make(registerChannelRequest.categoryId()))
                .guildId(GuildIdJpaVO.make(registerChannelRequest.guildId()))
                .build();
    }
}

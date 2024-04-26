package harmony.communityservice.guild.channel.mapper;

import harmony.communityservice.guild.category.domain.CategoryId;
import harmony.communityservice.guild.channel.dto.RegisterChannelRequest;
import harmony.communityservice.guild.channel.domain.Channel;
import harmony.communityservice.guild.guild.domain.GuildId;

public class ToChannelMapper {

    public static Channel convert(RegisterChannelRequest registerChannelRequest) {
        return Channel.builder()
                .name(registerChannelRequest.name())
                .type(registerChannelRequest.type())
                .categoryId(CategoryId.make(registerChannelRequest.categoryId()))
                .guildId(GuildId.make(registerChannelRequest.guildId()))
                .build();
    }
}

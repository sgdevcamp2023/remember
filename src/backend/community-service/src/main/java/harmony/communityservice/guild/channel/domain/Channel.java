package harmony.communityservice.guild.channel.domain;

import harmony.communityservice.domain.Domain;
import harmony.communityservice.domain.ValueObject;
import harmony.communityservice.guild.category.domain.Category.CategoryId;
import harmony.communityservice.guild.channel.domain.Channel.ChannelId;
import harmony.communityservice.guild.guild.domain.Guild.GuildId;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Channel extends Domain<Channel, ChannelId> {

    private ChannelId channelId;

    private GuildId guildId;

    private CategoryId categoryId;
    private String name;

    private ChannelType type;

    @Builder
    public Channel(CategoryId categoryId, ChannelId channelId, GuildId guildId, String name, String type) {
        this.categoryId = categoryId;
        this.channelId = channelId;
        this.guildId = guildId;
        this.name = name;
        this.type = ChannelType.valueOf(type);
    }

    @Override
    public ChannelId getId() {
        return channelId;
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class ChannelId extends ValueObject<ChannelId> {
        private Long id;

        public static ChannelId make(Long channelId) {
            return new ChannelId(channelId);
        }

        @Override
        protected Object[] getEqualityFields() {
            return new Object[]{id};
        }
    }
}

package harmony.communityservice.guild.channel.domain;

import harmony.communityservice.common.domain.AggregateRoot;
import harmony.communityservice.guild.category.domain.CategoryId;
import harmony.communityservice.guild.category.domain.CategoryId.CategoryIdJavaType;
import harmony.communityservice.guild.channel.domain.ChannelId.ChannelIdJavaType;
import harmony.communityservice.guild.guild.domain.GuildId;
import harmony.communityservice.guild.guild.domain.GuildId.GuildIdJavaType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JavaType;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "channel", indexes = @Index(name = "idx__guild_id", columnList = "guild_id"))
public class Channel extends AggregateRoot<Channel, ChannelId> {

    @Id
    @Column(name = "channel_id")
    @JavaType(ChannelIdJavaType.class)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private ChannelId channelId;


    @Column(name = "guild_id")
    @JavaType(GuildIdJavaType.class)
    private GuildId guildId;

    @Column(name = "category_id")
    @JavaType(CategoryIdJavaType.class)
    private CategoryId categoryId;

    @Column(name = "channel_name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "channel_type")
    private ChannelType channelType;


    @Builder
    public Channel(CategoryId categoryId, String name, GuildId guildId,
                   String type) {
        this.categoryId = categoryId;
        this.name = name;
        this.guildId = guildId;
        this.channelType = ChannelType.valueOf(type);
    }

    @Override
    public ChannelId getId() {
        return channelId;
    }
}

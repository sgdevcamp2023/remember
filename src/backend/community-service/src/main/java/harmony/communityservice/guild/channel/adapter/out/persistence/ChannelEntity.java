package harmony.communityservice.guild.channel.adapter.out.persistence;

import harmony.communityservice.common.domainentity.AggregateRootEntity;
import harmony.communityservice.guild.category.adapter.out.persistence.CategoryIdJpaVO;
import harmony.communityservice.guild.category.adapter.out.persistence.CategoryIdJpaVO.CategoryIdJavaType;
import harmony.communityservice.guild.channel.adapter.out.persistence.ChannelIdJpaVO.ChannelIdJavaType;
import harmony.communityservice.guild.guild.adapter.out.persistence.GuildIdJpaVO;
import harmony.communityservice.guild.guild.adapter.out.persistence.GuildIdJpaVO.GuildIdJavaType;
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
public class ChannelEntity extends AggregateRootEntity<ChannelEntity, ChannelIdJpaVO> {

    @Id
    @Column(name = "channel_id")
    @JavaType(ChannelIdJavaType.class)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private ChannelIdJpaVO channelId;


    @Column(name = "guild_id")
    @JavaType(GuildIdJavaType.class)
    private GuildIdJpaVO guildId;

    @Column(name = "category_id")
    @JavaType(CategoryIdJavaType.class)
    private CategoryIdJpaVO categoryId;

    @Column(name = "channel_name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "channel_type")
    private ChannelTypeJpaEnum channelType;


    @Builder
    public ChannelEntity(CategoryIdJpaVO categoryId, String name, GuildIdJpaVO guildId,
                         String type) {
        this.categoryId = categoryId;
        this.name = name;
        this.guildId = guildId;
        this.channelType = ChannelTypeJpaEnum.valueOf(type);
    }

    @Override
    public ChannelIdJpaVO getId() {
        return channelId;
    }
}

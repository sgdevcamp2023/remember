package harmony.communityservice.community.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "channel_read", indexes = @Index(name = "idx__categoryId__guildId",columnList = "category_id, guild_id"))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChannelRead {

    @Id
    @Column(name = "channel_read_id")
    private Long channelReadId;

    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "guild_id")
    private Long guildId;

    @Column(name = "channel_name")
    private String name;

    @Enumerated(EnumType.STRING)
    private ChannelType type;

    @Builder
    public ChannelRead(Long channelReadId, Long categoryId, Long guildId, String name, ChannelType type) {
        this.channelReadId = channelReadId;
        this.categoryId = categoryId;
        this.guildId = guildId;
        this.name = name;
        this.type = type;
    }
}

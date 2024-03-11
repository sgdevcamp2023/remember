package harmony.communityservice.community.domain;

import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "channel",indexes = @Index(name = "idx__categoryId__guildId",columnList = "category_id, guild_id"))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Channel {

    @Id
    @Column(name = "channel_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long channelId;

    @ManyToOne
    @JoinColumn(name = "guild_id")
    private Guild guild;

    @Nullable
    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "channel_name")
    private String name;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "channel_type")
    private ChannelType type;

    @Builder
    public Channel(Guild guild, Long categoryId, String name,
                   String type) {
        this.guild = guild;
        this.categoryId = categoryId;
        this.name = name;
        this.createdAt = LocalDateTime.now();
        this.type = ChannelType.valueOf(type);
    }
}

package harmony.communityservice.guild.domain;

import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "channel")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Channel {

    @Id
    @Column(name = "channel_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long channelId;

    @Nullable
    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "channel_name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "channel_type")
    private ChannelType type;

    @Embedded
    private CreationTime creationTime;

    @Builder
    public Channel(Long categoryId, String name,
                   String type) {
        this.categoryId = categoryId;
        this.name = name;
        this.creationTime = new CreationTime();
        this.type = ChannelType.valueOf(type);
    }
}

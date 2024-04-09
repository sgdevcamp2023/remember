package harmony.communityservice.guild.domain;

import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.criteria.CriteriaBuilder.In;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Channel {

    @Nullable
    @Column(name = "category_id")
    private Integer categoryId;

    @Column(name = "channel_name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "channel_type")
    private ChannelType type;

    @Embedded
    private CreationTime creationTime;

    @Builder
    public Channel(Integer categoryId, String name,
                   String type) {
        this.categoryId = categoryId;
        this.name = name;
        this.creationTime = new CreationTime();
        this.type = ChannelType.valueOf(type);
    }
}

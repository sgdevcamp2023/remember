package harmony.communityservice.common.outbox;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import harmony.communityservice.common.event.dto.produce.ExternalEvent;
import harmony.communityservice.guild.channel.adapter.out.persistence.ChannelTypeJpaEnum;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.Instant;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@JsonInclude(Include.NON_NULL)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ExternalEventRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventId;

    private SentType sentType;

    private ExternalEventType type;

    private Instant createdAt;

    private Long guildId;

    private ChannelTypeJpaEnum channelType;

    private Long channelId;

    private String channelName;

    private Long categoryId;

    private Long guildReadId;

    private String name;

    private String profile;

    @Builder
    public ExternalEventRecord(Long categoryId, Long channelId, String channelName, ChannelTypeJpaEnum channelType,
                               Long guildId, Long guildReadId, String name, String profile,
                               SentType sentType,
                               ExternalEventType type) {
        this.categoryId = categoryId;
        this.channelId = channelId;
        this.channelName = channelName;
        this.channelType = channelType;
        this.createdAt = Instant.now();
        this.guildId = guildId;
        this.guildReadId = guildReadId;
        this.name = name;
        this.profile = profile;
        this.sentType = sentType;
        this.type = type;
    }

    public ExternalEvent make() {
        return ExternalEvent.builder()
                .name(name)
                .categoryId(categoryId)
                .guildId(guildId)
                .channelId(channelId)
                .channelName(channelName)
                .type(type)
                .profile(profile)
                .guildReadId(guildReadId)
                .channelType(channelType)
                .build();
    }
}

package harmony.communityservice.common.outbox;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.Instant;
import lombok.Builder;
import lombok.Getter;

@Getter
@JsonInclude(Include.NON_NULL)
public class ExternalEventRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventId;

    private SentType sentType;

    private EventType type;

    private Instant createdAt;

    private Long guildId;

    private String channelType;

    private Long channelId;

    private String channelName;

    private Long categoryId;

    private Long guildReadId;

    private String name;

    private String profile;

    @Builder
    public ExternalEventRecord(Long categoryId, Long channelId, String channelName, String channelType,
                               Long guildId, Long guildReadId, String name, String profile,
                               SentType sentType,
                               EventType type) {
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
}

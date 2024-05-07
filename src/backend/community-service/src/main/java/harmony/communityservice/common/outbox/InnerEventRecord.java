package harmony.communityservice.common.outbox;

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
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InnerEventRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventId;

    private Long userId;

    private Long channelId;

    private Long guildId;

    private String channelName;

    private Long categoryId;

    private String channelType;

    private Long boardId;

    private String guildName;

    private String guildProfile;

    private InnerEventType type;
    private Instant createdAt;
    private SentType sentType;

    @Builder
    public InnerEventRecord(Long boardId, Long categoryId, Long channelId, String channelName, String channelType,
                            Long guildId, String guildName, String guildProfile, InnerEventType type, Long userId,
                            SentType sentType) {
        this.boardId = boardId;
        this.categoryId = categoryId;
        this.channelId = channelId;
        this.channelName = channelName;
        this.channelType = channelType;
        this.guildId = guildId;
        this.guildName = guildName;
        this.guildProfile = guildProfile;
        this.type = type;
        this.userId = userId;
        this.createdAt = Instant.now();
        this.sentType = sentType;
    }
}

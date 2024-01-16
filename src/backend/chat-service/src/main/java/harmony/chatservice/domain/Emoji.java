package harmony.chatservice.domain;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "emojis")
public class Emoji {

    @Transient
    public static final String SEQUENCE_NAME = "emoji_sequence";

    @Id
    private Long emojiId;

    @Field
    private Long guildId;

    @Field
    private Long channelId;

    @Field
    private Long roomId;

    @Field
    private Long parentId;

    @Field
    private Long typeId;

    @Field
    private String type;

    @Field
    private List<Long> userIds;

    @Builder
    public Emoji(Long guildId, Long channelId, Long roomId, Long parentId, Long typeId, String type,
                 List<Long> userIds) {
        this.guildId = guildId;
        this.channelId = channelId;
        this.roomId = roomId;
        this.parentId = parentId;
        this.typeId = typeId;
        this.type = type;
        this.userIds = userIds;
    }
}

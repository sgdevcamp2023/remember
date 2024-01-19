package harmony.chatservice.domain;

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
    private Long communityMessageId;

    @Field
    private Long directMessageId;

    @Field
    private Long typeId;

    @Field
    private String type;

    @Field
    private Long userId;

    @Builder
    public Emoji(Long guildId, Long channelId, Long roomId, Long communityMessageId, Long directMessageId,
                 Long typeId, String type, Long userId) {

        this.guildId = guildId;
        this.channelId = channelId;
        this.roomId = roomId;
        this.communityMessageId = communityMessageId;
        this.directMessageId = directMessageId;
        this.typeId = typeId;
        this.type = type;
        this.userId = userId;
    }
}
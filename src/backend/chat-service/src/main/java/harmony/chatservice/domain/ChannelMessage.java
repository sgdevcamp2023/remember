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
@Document(collection = "channelMessages")
public class ChannelMessage extends BaseModel {

    @Transient
    public static final String SEQUENCE_NAME = "channelMessages_sequence";

    @Id
    private Long messageId;

    @Field
    private Long guildId;

    @Field
    private Long channelId;

    @Field
    private Long userId;

    @Field
    private Long parentId;

    @Field
    private String profileImage;

    @Field
    private String type;

    @Field
    private String senderName;

    @Field
    private String message;

    @Field
    private boolean delCheck;

    @Builder
    public ChannelMessage(Long guildId, Long channelId, Long userId, Long parentId, String profileImage, String type, String senderName, String message, boolean delCheck) {
        this.guildId = guildId;
        this.channelId = channelId;
        this.userId = userId;
        this.parentId = parentId;
        this.profileImage = profileImage;
        this.type = type;
        this.senderName = senderName;
        this.message = message;
        this.delCheck = delCheck;
    }

    @Override
    public String toString() {
        return "MyClass{" +
                "messageId='" + messageId + '\'' +
                "guildId='" + guildId + '\'' +
                "channelId='" + channelId + '\'' +
                "profileImage='" + profileImage + '\'' +
                "type='" + type + '\'' +
                "userId='" + userId + '\'' +
                "senderName='" + senderName + '\'' +
                ", message=" + message +
                '}';
    }
}
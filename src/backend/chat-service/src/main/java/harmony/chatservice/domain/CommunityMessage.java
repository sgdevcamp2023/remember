package harmony.chatservice.domain;

import java.time.LocalDateTime;
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
@Document(collection = "communityMessages")
public class CommunityMessage extends BaseModel {

    @Transient
    public static final String SEQUENCE_NAME = "communityMessages_sequence";

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

    @Field
    private List<String> files;

    @Builder
    public CommunityMessage(Long guildId, Long channelId, Long userId, Long parentId, String profileImage, String type,
                            String senderName, String message, boolean delCheck, List<String> files) {

        this.guildId = guildId;
        this.channelId = channelId;
        this.userId = userId;
        this.parentId = parentId;
        this.profileImage = profileImage;
        this.type = type;
        this.senderName = senderName;
        this.message = message;
        this.delCheck = delCheck;
        this.files = files;
    }

    public void modify(String message, String type) {
        this.message = message;
        this.type = type;
        this.setModifiedAt(LocalDateTime.now());
    }

    public void delete(String type) {
        this.type = type;
        this.delCheck = true;
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
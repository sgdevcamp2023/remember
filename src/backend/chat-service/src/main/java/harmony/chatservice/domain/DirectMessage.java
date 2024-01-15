package harmony.chatservice.domain;

import java.time.LocalDateTime;
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
@Document(collection = "directMessages")
public class DirectMessage extends BaseModel{

    @Transient
    public static final String SEQUENCE_NAME = "directMessages_sequence";

    @Id
    private Long messageId;

    @Field
    private Long roomId;

    @Field
    private Long parentId;

    @Field
    private Long userId;

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

    public void modify(String message) {
        this.message = message;
        this.setModifiedAt(LocalDateTime.now());
    }

    public void delete(String type) {
        this.type = type;
        this.delCheck = true;
    }

    @Builder
    public DirectMessage(Long roomId, Long parentId, Long userId, String profileImage, String type, String senderName, String message) {
        this.roomId = roomId;
        this.parentId = parentId;
        this.userId = userId;
        this.profileImage =profileImage;
        this.type =type;
        this.senderName = senderName;
        this.message = message;
        this.delCheck = false;
    }

    @Override
    public String toString() {
        return "MyClass{" +
                "messageId='" + messageId + '\'' +
                "roomId='" + roomId + '\'' +
                "profileImage='" + profileImage + '\'' +
                "type='" + type + '\'' +
                "userId='" + userId + '\'' +
                "senderName='" + senderName + '\'' +
                "message=" + message +
                '}';
    }
}
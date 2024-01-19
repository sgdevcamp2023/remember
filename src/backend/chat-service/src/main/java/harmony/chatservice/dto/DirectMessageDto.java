package harmony.chatservice.dto;


import harmony.chatservice.domain.DirectMessage;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DirectMessageDto {

    private Long messageId;
    private Long parentId;
    private Long roomId;
    private Long userId;
    private Long count;
    private String profileImage;
    private String type;
    private String senderName;
    private String message;
    private boolean delCheck;
    private List<String> files;
    private List<EmojiDto> emojis;

    public DirectMessageDto(DirectMessage directMessage) {
        this.messageId = directMessage.getMessageId();
        this.parentId = directMessage.getParentId();
        this.roomId = directMessage.getRoomId();
        this.userId = directMessage.getUserId();
        this.profileImage = directMessage.getProfileImage();
        this.type = directMessage.getType();
        this.senderName = directMessage.getSenderName();
        this.message = directMessage.getMessage();
        this.delCheck = directMessage.isDelCheck();
        this.files = directMessage.getFiles();
    }
}
package harmony.chatservice.dto;

import harmony.chatservice.domain.CommunityMessage;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommunityMessageDto {

    private Long messageId;
    private Long guildId;
    private Long channelId;
    private Long userId;
    private Long parentId;
    private String profileImage;
    private String type;
    private String senderName;
    private String message;
    private LocalDateTime createdAt;
    private boolean delCheck;
    private List<String> files;

    @Builder
    public CommunityMessageDto(CommunityMessage message) {
        this.messageId = message.getMessageId();
        this.guildId = message.getGuildId();
        this.channelId = message.getChannelId();
        this.userId = message.getUserId();
        this.parentId = message.getParentId();
        this.profileImage = message.getProfileImage();
        this.type = message.getType();
        this.senderName = message.getSenderName();
        this.message = message.getMessage();
        this.createdAt = message.getCreatedAt();
        this.delCheck = message.isDelCheck();
        this.files = message.getFiles();
    }
}

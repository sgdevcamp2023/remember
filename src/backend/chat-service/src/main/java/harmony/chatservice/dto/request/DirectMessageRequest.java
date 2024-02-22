package harmony.chatservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DirectMessageRequest {

    private Long roomId;
    private Long userId;
    private Long parentId;
    private String profileImage;
    private String type;
    private String senderName;
    private String message;
}
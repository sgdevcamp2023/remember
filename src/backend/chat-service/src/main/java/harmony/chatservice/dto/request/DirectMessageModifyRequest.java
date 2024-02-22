package harmony.chatservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DirectMessageModifyRequest {

    private Long roomId;
    private Long messageId;
    private String message;
    private String type;
}

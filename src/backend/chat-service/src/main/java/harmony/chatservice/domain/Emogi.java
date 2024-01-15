package harmony.chatservice.domain;

import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

public class Emogi {

    @Id
    private Long emogiId;

    // 이모지가 달린 messageId
    @Field
    private Long parentId;

    @Field
    private Long emogiCount;

    @Field
    private List<Long> userIds;
}

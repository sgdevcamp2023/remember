package harmony.chatservice.dto.request;

import harmony.chatservice.domain.Emoji;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmojiDto {

    private Long emojiId;
    private Long guildId;
    private Long channelId;
    private Long roomId;
    private Long messageId;
    private Long userId;
    private Long typeId;
    private String type;

    @Builder
    public EmojiDto(Emoji emoji) {
        this.emojiId = emoji.getEmojiId();
        this.guildId = emoji.getGuildId();
        this.channelId = emoji.getChannelId();
        this.roomId = emoji.getRoomId();
        this.messageId = emoji.getMessageId();
        this.userId = emoji.getUserId();
        this.typeId = emoji.getTypeId();
        this.type = emoji.getType();
    }
}

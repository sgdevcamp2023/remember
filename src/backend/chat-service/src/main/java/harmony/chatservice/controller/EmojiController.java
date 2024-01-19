package harmony.chatservice.controller;

import harmony.chatservice.domain.Emoji;
import harmony.chatservice.dto.request.EmojiDeleteRequest;
import harmony.chatservice.dto.EmojiDto;
import harmony.chatservice.dto.request.EmojiRequest;
import harmony.chatservice.service.EmojiService;
import harmony.chatservice.service.kafka.MessageProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EmojiController {

    private final EmojiService emojiService;
    private final MessageProducerService producerService;

    @MessageMapping("/emoji/save")
    public void saveEmoji(EmojiRequest emojiRequest) {
        Emoji emoji = emojiService.saveEmoji(emojiRequest);
        EmojiDto emojiDto = new EmojiDto(emoji);
        producerService.sendMessageForEmoji(emojiDto);
    }

    @MessageMapping("/emoji/delete")
    public void deleteEmoji(EmojiDeleteRequest deleteRequest) {
        EmojiDto emojiDto = emojiService.deleteEmoji(deleteRequest);
        producerService.sendMessageForEmoji(emojiDto);
    }
}
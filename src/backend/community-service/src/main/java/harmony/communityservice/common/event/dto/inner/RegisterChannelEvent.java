package harmony.communityservice.common.event.dto.inner;

public record RegisterChannelEvent(Long guildId, String channelName, Long userId, Long categoryId, String type) {
}

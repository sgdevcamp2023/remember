package harmony.communityservice.common.event.dto.inner;

import java.util.List;

public record DeleteBoardsInGuildEvent(List<Long> channelIds) {
}

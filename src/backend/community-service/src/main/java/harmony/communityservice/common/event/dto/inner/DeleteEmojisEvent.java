package harmony.communityservice.common.event.dto.inner;

import java.util.List;

public record DeleteEmojisEvent(List<Long> boardIds) {
}

package harmony.communityservice.common.event.dto.inner;

import java.util.List;

public record DeleteCommentsEvent(List<Long> boardIds) {
}

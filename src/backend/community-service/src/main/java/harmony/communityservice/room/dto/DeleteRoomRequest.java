package harmony.communityservice.room.dto;

import jakarta.validation.constraints.NotNull;

public record DeleteRoomRequest(@NotNull Long roomId, @NotNull Long firstUser, @NotNull Long secondUser) {
}

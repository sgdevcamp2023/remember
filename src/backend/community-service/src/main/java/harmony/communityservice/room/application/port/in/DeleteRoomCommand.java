package harmony.communityservice.room.application.port.in;

public record DeleteRoomCommand(Long roomId, Long firstUser, Long secondUser) {
}

package harmony.communityservice.user.application.port.in;

public record ModifyUserNicknameCommand(Long userId, String nickname) {
}

package harmony.communityservice.user.application.port.in;

public record RegisterUserCommand(Long userId, String email, String nickname, String profile) {
}

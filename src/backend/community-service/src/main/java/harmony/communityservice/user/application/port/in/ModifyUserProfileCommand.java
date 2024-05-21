package harmony.communityservice.user.application.port.in;

public record ModifyUserProfileCommand(Long userId, String profile) {
}

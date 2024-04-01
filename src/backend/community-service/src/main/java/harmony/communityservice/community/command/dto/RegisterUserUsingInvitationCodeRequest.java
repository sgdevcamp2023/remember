package harmony.communityservice.community.command.dto;

public record RegisterUserUsingInvitationCodeRequest(String invitationCode, Long userId) {
}
package harmony.communityservice.guild.guild.dto;

public record RegisterUserUsingInvitationCodeRequest(String invitationCode, Long userId) {
}
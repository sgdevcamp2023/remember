package harmony.communityservice.guild.guild.application.port.in;

public record JoinGuildCommand(Long userId, String invitationCode) {
}

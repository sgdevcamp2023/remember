package harmony.communityservice.guild.guild.application.port.in;

public record ModifyGuildNicknameCommand(Long guildId, Long userId, String nickname) {
}

package harmony.communityservice.guild.guild.application.port.in;

public interface VerifyGuildManagerQuery {
    void verify(Long guildId, Long managerId);
}

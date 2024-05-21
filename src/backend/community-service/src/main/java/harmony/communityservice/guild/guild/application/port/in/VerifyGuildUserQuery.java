package harmony.communityservice.guild.guild.application.port.in;


public interface VerifyGuildUserQuery {

    void verify(VerifyGuildMemberCommand verifyGuildMemberCommand);
}

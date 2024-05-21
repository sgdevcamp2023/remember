package harmony.communityservice.guild.guild.application.port.in;

public interface LoadInvitationCodeQuery {

    String load(LoadInvitationCodeCommand searchGuildInvitationCodeCommand);
}

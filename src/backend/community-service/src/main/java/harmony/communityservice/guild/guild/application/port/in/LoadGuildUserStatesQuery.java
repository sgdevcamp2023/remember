package harmony.communityservice.guild.guild.application.port.in;

public interface LoadGuildUserStatesQuery {

    LoadGuildUserStatesResponse load(LoadGuildUserStatesCommand loadGuildUserStatesCommand);
}

package harmony.communityservice.guild.channel.application.port.in;

public interface DeleteGuildChannelsUseCase {

    void deleteByGuildId(Long guildId);
}

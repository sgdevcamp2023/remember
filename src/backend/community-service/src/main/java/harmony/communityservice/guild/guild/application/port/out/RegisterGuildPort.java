package harmony.communityservice.guild.guild.application.port.out;

import harmony.communityservice.guild.guild.domain.Guild;
import harmony.communityservice.user.domain.User.UserId;

public interface RegisterGuildPort {
    Long register(Guild guild);

    Guild join(String code, UserId userId);
}

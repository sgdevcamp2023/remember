package harmony.communityservice.community.mapper;

import harmony.communityservice.community.command.dto.RegisterUserReadRequest;
import harmony.communityservice.community.domain.Guild;
import harmony.communityservice.community.domain.User;

public class ToRegisterUserReadRequestMapper {

    public static RegisterUserReadRequest convert(Guild guild, User user) {
        return RegisterUserReadRequest.builder()
                .guildId(guild.getGuildId())
                .userId(user.getUserId())
                .nickname(user.getNickname())
                .profile(user.getProfile())
                .build();
    }
}

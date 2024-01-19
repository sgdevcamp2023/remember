package harmony.communityservice.community.mapper;

import harmony.communityservice.community.command.dto.UserReadRequestDto;
import harmony.communityservice.community.domain.Guild;
import harmony.communityservice.community.domain.User;

public class ToUserReadRequestDtoMapper {

    public static UserReadRequestDto convert(Guild guild, User user) {
        return UserReadRequestDto.builder()
                .guildId(guild.getGuildId())
                .userId(user.getUserId())
                .nickname(user.getNickname())
                .profile(user.getProfile())
                .build();
    }
}

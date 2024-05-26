package harmony.communityservice.room.application.service;

import harmony.communityservice.guild.guild.domain.GuildRead;
import harmony.communityservice.room.application.port.in.LoadUserStateResponse;
import harmony.communityservice.user.domain.CommonUserInfo;
import harmony.communityservice.user.domain.User;

public class SearchUserStateResponseMapper {

    public static LoadUserStateResponse convert(User user, String status) {
        CommonUserInfo commonUserInfo = user.getUserInfo().getCommonUserInfo();
        return LoadUserStateResponse.builder()
                .userId(user.getUserId().getId())
                .profile(commonUserInfo.getProfile())
                .state(status)
                .userName(commonUserInfo.getNickname())
                .build();
    }

    public static LoadUserStateResponse convert(GuildRead guildRead) {
        return LoadUserStateResponse.builder()
                .userName(guildRead.getCommonUserInfo().getNickname())
                .profile(guildRead.getCommonUserInfo().getProfile())
                .userId(guildRead.getUserId().getId())
                .build();
    }
}

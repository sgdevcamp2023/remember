package harmony.communityservice.room.application.service;

import harmony.communityservice.guild.guild.domain.GuildRead;
import harmony.communityservice.room.application.port.in.SearchUserStateResponse;
import harmony.communityservice.user.domain.CommonUserInfo;
import harmony.communityservice.user.domain.User;

public class SearchUserStateResponseMapper {

    public static SearchUserStateResponse convert(User user, String status) {
        CommonUserInfo commonUserInfo = user.getUserInfo().getCommonUserInfo();
        return SearchUserStateResponse.builder()
                .userId(user.getUserId().getId())
                .profile(commonUserInfo.getProfile())
                .state(status)
                .userName(commonUserInfo.getNickname())
                .build();
    }

    public static SearchUserStateResponse convert(GuildRead guildRead) {
        return SearchUserStateResponse.builder()
                .userName(guildRead.getCommonUserInfo().getNickname())
                .profile(guildRead.getCommonUserInfo().getProfile())
                .userId(guildRead.getUserId().getId())
                .build();
    }
}

package harmony.communityservice.common.client;

import harmony.communityservice.common.dto.LoadDmUserStateFeignResponse;
import harmony.communityservice.common.dto.LoadUserStateInGuildAndChannelFeignResponse;
import harmony.communityservice.common.dto.LoadUserStatusInDmRoomRequest;
import harmony.communityservice.guild.guild.adapter.in.web.LoadUserStatesInGuildRequest;

public interface UserStatusClient {
    LoadUserStateInGuildAndChannelFeignResponse getCommunityUsersState(
            LoadUserStatesInGuildRequest searchUserStatesInGuildRequest);

    LoadDmUserStateFeignResponse userStates(
            LoadUserStatusInDmRoomRequest searchUserStatusInDmRoomRequest);
}

package harmony.communityservice.common.client;

import harmony.communityservice.common.dto.SearchDmUserStateFeignResponse;
import harmony.communityservice.common.dto.SearchUserStateInGuildAndRoomFeignResponse;
import harmony.communityservice.common.dto.SearchUserStatusInDmRoomRequest;
import harmony.communityservice.guild.guild.adapter.in.web.LoadUserStatesInGuildRequest;

public interface UserStatusClient {
    SearchUserStateInGuildAndRoomFeignResponse userStatus(
            LoadUserStatesInGuildRequest searchUserStatesInGuildRequest);

    SearchDmUserStateFeignResponse getCommunityUsersState(
            SearchUserStatusInDmRoomRequest searchUserStatusInDmRoomRequest);
}

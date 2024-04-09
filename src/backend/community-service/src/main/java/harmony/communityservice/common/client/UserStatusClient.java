package harmony.communityservice.common.client;

import harmony.communityservice.common.dto.SearchDmUserStateFeignResponse;
import harmony.communityservice.common.dto.SearchUserStateInGuildAndRoomFeignResponse;
import harmony.communityservice.room.dto.SearchUserStatusInDmRoomRequest;
import harmony.communityservice.guild.guild.dto.SearchUserStatesInGuildRequest;

public interface UserStatusClient {
    SearchUserStateInGuildAndRoomFeignResponse userStatus(
            SearchUserStatesInGuildRequest searchUserStatesInGuildRequest);

    SearchDmUserStateFeignResponse getCommunityUsersState(
            SearchUserStatusInDmRoomRequest searchUserStatusInDmRoomRequest);
}

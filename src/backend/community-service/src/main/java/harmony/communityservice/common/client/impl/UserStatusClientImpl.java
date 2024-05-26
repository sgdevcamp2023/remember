package harmony.communityservice.common.client.impl;

import harmony.communityservice.common.client.UserStatusClient;
import harmony.communityservice.common.client.feign.UserStatusFeignClient;
import harmony.communityservice.common.dto.SearchDmUserStateFeignResponse;
import harmony.communityservice.common.dto.SearchUserStateInGuildAndRoomFeignResponse;
import harmony.communityservice.common.dto.SearchUserStatusInDmRoomRequest;
import harmony.communityservice.guild.guild.adapter.in.web.LoadUserStatesInGuildRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserStatusClientImpl implements UserStatusClient {

    private final UserStatusFeignClient userStatusFeignClient;

    @Override
    public SearchUserStateInGuildAndRoomFeignResponse userStatus(
            LoadUserStatesInGuildRequest searchUserStatesInGuildRequest) {
        return userStatusFeignClient.userStatus(searchUserStatesInGuildRequest);
    }

    @Override
    public SearchDmUserStateFeignResponse getCommunityUsersState(
            SearchUserStatusInDmRoomRequest searchUserStatusInDmRoomRequest) {
        return userStatusFeignClient.getCommunityUsersState(searchUserStatusInDmRoomRequest);
    }
}

package harmony.communityservice.community.command.service.impl;

import harmony.communityservice.community.command.dto.ModifyUserNicknameRequest;
import harmony.communityservice.community.command.dto.ModifyUserProfileRequest;
import harmony.communityservice.community.command.dto.RegisterUserRequest;
import harmony.communityservice.community.command.repository.UserCommandRepository;
import harmony.communityservice.community.command.service.UserCommandService;
import harmony.communityservice.community.domain.User;
import harmony.communityservice.community.mapper.ToUserMapper;
import harmony.communityservice.community.query.service.UserQueryService;
import harmony.communityservice.community.query.service.UserReadQueryService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserCommandServiceImpl implements UserCommandService {

    private final UserCommandRepository userCommandRepository;
    private final UserQueryService userQueryService;
    private final UserReadQueryService userReadQueryService;

    @Override
    public void register(RegisterUserRequest registerUserRequest) {
        User user = ToUserMapper.convert(registerUserRequest);
        userCommandRepository.save(user);
    }

    @Override
    public void modifyProfile(ModifyUserProfileRequest modifyUserProfileRequest) {
        modifyUserInfo(modifyUserProfileRequest.userId(), modifyUserProfileRequest.profile());
    }

    @Override
    public void modifyNickname(ModifyUserNicknameRequest modifyUserNicknameRequest) {
        modifyUserInfo(modifyUserNicknameRequest.userId(), modifyUserNicknameRequest.nickname());
    }

    private void modifyUserInfo(Long userId, String userInfo) {
        User targetUser = userQueryService.searchByUserId(userId);
        targetUser.modifyProfile(userInfo);
        userReadQueryService.searchListByUserId(userId)
                .forEach(target -> target.modifyProfile(userInfo));
    }
}

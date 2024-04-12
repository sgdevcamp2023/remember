package harmony.communityservice.user.service.command.impl;

import harmony.communityservice.common.exception.NotFoundDataException;
import harmony.communityservice.user.domain.User;
import harmony.communityservice.user.dto.ModifyUserNicknameRequest;
import harmony.communityservice.user.dto.ModifyUserProfileRequest;
import harmony.communityservice.user.dto.RegisterUserRequest;
import harmony.communityservice.user.mapper.ToUserMapper;
import harmony.communityservice.user.repository.command.UserCommandRepository;
import harmony.communityservice.user.repository.command.UserReadCommandRepository;
import harmony.communityservice.user.service.command.UserCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
public class UserCommandServiceImpl implements UserCommandService {

    private final UserCommandRepository userCommandRepository;
    private final UserReadCommandRepository userReadCommandRepository;

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
        User targetUser = userCommandRepository.findById(userId).orElseThrow(NotFoundDataException::new);
        targetUser.modifyProfile(userInfo);
        userReadCommandRepository.findListByUserId(userId)
                .forEach(target -> target.modifyProfile(userInfo));
    }
}

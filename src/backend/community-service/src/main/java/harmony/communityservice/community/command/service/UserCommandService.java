package harmony.communityservice.community.command.service;

import harmony.communityservice.community.command.dto.ModifyUserNicknameRequest;
import harmony.communityservice.community.command.dto.ModifyUserProfileRequest;
import harmony.communityservice.community.command.dto.RegisterUserRequest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserCommandService {
    void register(RegisterUserRequest registerUserRequest);

    void modifyProfile(ModifyUserProfileRequest modifyUserProfileRequest);

    void modifyNickname(ModifyUserNicknameRequest modifyUserNicknameRequest);
}

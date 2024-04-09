package harmony.communityservice.user.service.command;

import harmony.communityservice.user.dto.ModifyUserNicknameRequest;
import harmony.communityservice.user.dto.ModifyUserProfileRequest;
import harmony.communityservice.user.dto.RegisterUserRequest;

public interface UserCommandService {

    void register(RegisterUserRequest registerUserRequest);

    void modifyProfile(ModifyUserProfileRequest modifyUserProfileRequest);

    void modifyNickname(ModifyUserNicknameRequest modifyUserNicknameRequest);
}

package harmony.communityservice.user.application.port.in;

public interface ModifyUserInfoUseCase {

    void modifyProfile(ModifyUserProfileCommand modifyUserProfileCommand);

    void modifyNickname(ModifyUserNicknameCommand modifyUserNicknameCommand);
}

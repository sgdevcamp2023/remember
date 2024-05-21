package harmony.communityservice.user.application.service;

import harmony.communityservice.common.annotation.UseCase;
import harmony.communityservice.user.application.port.in.ModifyUserInfoUseCase;
import harmony.communityservice.user.application.port.in.ModifyUserNicknameCommand;
import harmony.communityservice.user.application.port.in.ModifyUserProfileCommand;
import harmony.communityservice.user.application.port.in.RegisterUserCommand;
import harmony.communityservice.user.application.port.in.RegisterUserUseCase;
import harmony.communityservice.user.application.port.out.LoadUserCommandPort;
import harmony.communityservice.user.application.port.out.LoadUserQueryPort;
import harmony.communityservice.user.application.port.out.ModifyUserInfoPort;
import harmony.communityservice.user.application.port.out.RegisterUserPort;
import harmony.communityservice.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@Transactional
@RequiredArgsConstructor
class UserCommandService implements RegisterUserUseCase, ModifyUserInfoUseCase {

    private final RegisterUserPort registerUserPort;
    private final LoadUserCommandPort loadUserPort;
    private final ModifyUserInfoPort modifyUserInfoPort;

    @Override
    public void register(RegisterUserCommand registerUserCommand) {
        User user = UserMapper.convert(registerUserCommand);
        registerUserPort.register(user);
    }

    @Override
    public void modifyProfile(ModifyUserProfileCommand modifyUserProfileCommand) {
        User findUser = loadUserPort.loadUser(modifyUserProfileCommand.userId());
        User modifedUser = findUser.modifiedProfile(modifyUserProfileCommand.profile());
        modifyUserInfoPort.modifyUserInfo(modifedUser);
    }

    @Override
    public void modifyNickname(ModifyUserNicknameCommand modifyUserNicknameCommand) {
        User findUser = loadUserPort.loadUser(modifyUserNicknameCommand.userId());
        User modifedUser = findUser.modifiedProfile(modifyUserNicknameCommand.nickname());
        modifyUserInfoPort.modifyUserInfo(modifedUser);
    }
}

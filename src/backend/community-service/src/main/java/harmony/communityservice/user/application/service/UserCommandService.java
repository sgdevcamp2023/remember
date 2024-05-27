package harmony.communityservice.user.application.service;

import harmony.communityservice.common.annotation.UseCase;
import harmony.communityservice.user.application.port.in.LoadUserUseCase;
import harmony.communityservice.user.application.port.in.ModifyUserInfoUseCase;
import harmony.communityservice.user.application.port.in.ModifyUserNicknameCommand;
import harmony.communityservice.user.application.port.in.ModifyUserProfileCommand;
import harmony.communityservice.user.application.port.in.RegisterUserCommand;
import harmony.communityservice.user.application.port.in.RegisterUserUseCase;
import harmony.communityservice.user.application.port.out.LoadUserCommandPort;
import harmony.communityservice.user.application.port.out.ModifyUserNicknamePort;
import harmony.communityservice.user.application.port.out.ModifyUserProfilePort;
import harmony.communityservice.user.application.port.out.RegisterUserPort;
import harmony.communityservice.user.domain.User;
import harmony.communityservice.user.domain.User.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@Transactional
@RequiredArgsConstructor
class UserCommandService implements RegisterUserUseCase, ModifyUserInfoUseCase, LoadUserUseCase {

    private final RegisterUserPort registerUserPort;
    private final LoadUserCommandPort loadUserPort;
    private final ModifyUserProfilePort modifyUserProfilePort;
    private final ModifyUserNicknamePort modifyUserNicknamePort;

    @Override
    public void register(RegisterUserCommand registerUserCommand) {
        User user = UserMapper.convert(registerUserCommand);
        registerUserPort.register(user);
    }

    @Override
    public void modifyProfile(ModifyUserProfileCommand modifyUserProfileCommand) {
        modifyUserProfilePort.modifyProfile(UserId.make(modifyUserProfileCommand.userId()),
                modifyUserProfileCommand.profile());
    }

    @Override
    public void modifyNickname(ModifyUserNicknameCommand modifyUserNicknameCommand) {
        modifyUserNicknamePort.modifyNickname(UserId.make(modifyUserNicknameCommand.userId()),
                modifyUserNicknameCommand.nickname());
    }

    @Override
    public User loadUser(Long userId) {
        return loadUserPort.loadUser(userId);
    }
}

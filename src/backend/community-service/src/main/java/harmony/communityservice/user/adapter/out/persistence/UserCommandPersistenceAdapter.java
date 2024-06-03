package harmony.communityservice.user.adapter.out.persistence;

import harmony.communityservice.common.annotation.PersistenceAdapter;
import harmony.communityservice.common.exception.NotFoundDataException;
import harmony.communityservice.user.application.port.out.LoadUserCommandPort;
import harmony.communityservice.user.application.port.out.ModifyUserNicknamePort;
import harmony.communityservice.user.application.port.out.ModifyUserProfilePort;
import harmony.communityservice.user.application.port.out.RegisterUserPort;
import harmony.communityservice.user.domain.User;
import harmony.communityservice.user.domain.User.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@PersistenceAdapter
@RequiredArgsConstructor
class UserCommandPersistenceAdapter implements RegisterUserPort, LoadUserCommandPort, ModifyUserProfilePort,
        ModifyUserNicknamePort {

    private final UserCommandRepository userCommandRepository;

    @Override
    public void register(User user) {
        UserEntity userEntity = UserEntityMapper.convert(user);
        userCommandRepository.save(userEntity);
    }

    @Override
    public User loadUser(Long userId) {
        UserEntity userEntity = userCommandRepository.findById(UserIdJpaVO.make(userId))
                .orElseThrow(NotFoundDataException::new);
        return UserMapper.convert(userEntity);
    }

    @Override
    public void modifyNickname(UserId userId, String nickname) {
        userCommandRepository.updateNickname(nickname,UserIdJpaVO.make(userId.getId()));
    }

    @Override
    public void modifyProfile(UserId userId, String profile) {
        userCommandRepository.updateProfile(profile,UserIdJpaVO.make(userId.getId()));
    }
}

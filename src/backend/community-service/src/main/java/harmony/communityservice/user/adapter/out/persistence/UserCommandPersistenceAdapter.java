package harmony.communityservice.user.adapter.out.persistence;

import harmony.communityservice.common.annotation.PersistenceAdapter;
import harmony.communityservice.common.exception.NotFoundDataException;
import harmony.communityservice.user.application.port.out.LoadUserCommandPort;
import harmony.communityservice.user.application.port.out.ModifyUserInfoPort;
import harmony.communityservice.user.application.port.out.RegisterUserPort;
import harmony.communityservice.user.domain.User;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
class UserCommandPersistenceAdapter implements RegisterUserPort, LoadUserCommandPort, ModifyUserInfoPort {

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
    public void modifyUserInfo(User user) {
        UserEntity userEntity = UserEntityMapper.convert(user);
        userCommandRepository.save(userEntity);
    }
}

package harmony.communityservice.user.adapter.out.persistence;

import harmony.communityservice.common.annotation.PersistenceAdapter;
import harmony.communityservice.common.exception.NotFoundDataException;
import harmony.communityservice.user.application.port.out.LoadUserQueryPort;
import harmony.communityservice.user.domain.User;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class UserQueryPersistenceAdapter implements LoadUserQueryPort {

    private final UserQueryRepository userQueryRepository;

    @Override
    public User loadUser(Long userId) {
        UserJpaEntity userJpaEntity = userQueryRepository.findById(UserIdJpaVO.make(userId))
                .orElseThrow(NotFoundDataException::new);
        return UserMapper.convert(userJpaEntity);
    }
}

package harmony.communityservice.user.service.query.impl;

import harmony.communityservice.common.exception.NotFoundDataException;
import harmony.communityservice.user.domain.User;
import harmony.communityservice.user.domain.UserId;
import harmony.communityservice.user.repository.query.UserQueryRepository;
import harmony.communityservice.user.service.query.UserQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserQueryServiceImpl implements UserQueryService {

    private final UserQueryRepository userQueryRepository;

    @Override
    public User searchByUserId(UserId userId) {
        return userQueryRepository.findById(userId).orElseThrow(NotFoundDataException::new);
    }
}

package harmony.communityservice.community.query.service.impl;

import harmony.communityservice.common.exception.NotFoundDataException;
import harmony.communityservice.community.domain.User;
import harmony.communityservice.community.query.repository.UserQueryRepository;
import harmony.communityservice.community.query.service.UserQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserQueryServiceImpl implements UserQueryService {

    private final UserQueryRepository userQueryRepository;

    @Override
    public User searchByUserId(Long userId) {
        return userQueryRepository.findById(userId).orElseThrow(NotFoundDataException::new);
    }
}

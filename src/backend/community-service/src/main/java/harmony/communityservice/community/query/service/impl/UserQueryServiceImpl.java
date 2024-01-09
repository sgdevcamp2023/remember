package harmony.communityservice.community.query.service.impl;

import harmony.communityservice.community.domain.User;
import harmony.communityservice.community.query.repository.UserQueryRepository;
import harmony.communityservice.community.query.service.UserQueryService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserQueryServiceImpl implements UserQueryService {

    private final UserQueryRepository userQueryRepository;

    @Override
    public User findUser(Long userId) {
        return userQueryRepository.findById(userId).orElseThrow();
    }
}

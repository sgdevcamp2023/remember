package harmony.communityservice.community.query.service.impl;

import harmony.communityservice.community.query.repository.UserReadQueryRepository;
import harmony.communityservice.community.query.service.UserReadQueryService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserReadQueryServiceImpl implements UserReadQueryService {

    private final UserReadQueryRepository userReadQueryRepository;

    @Override
    public void existsUserIdAndGuildId(long userId, long guildId) {
        if (userReadQueryRepository.existByUserIdAndGuildId(userId, guildId)) {
            throw new IllegalStateException();
        }
    }
}

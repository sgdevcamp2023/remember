package harmony.communityservice.community.query.service.impl;

import harmony.communityservice.common.exception.NotFoundDataException;
import harmony.communityservice.community.domain.UserRead;
import harmony.communityservice.community.query.repository.UserReadQueryRepository;
import harmony.communityservice.community.query.service.UserReadQueryService;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserReadQueryServiceImpl implements UserReadQueryService {

    private final UserReadQueryRepository userReadQueryRepository;

    @Override
    public void existsUserIdAndGuildId(long userId, long guildId) {
        if (!userReadQueryRepository.existByUserIdAndGuildId(userId, guildId)) {
            throw new NotFoundDataException();
        }
    }

    @Override
    public UserRead findUserReadIdAndGuildId(long userId, long guildId) {
        return userReadQueryRepository.findByUserIdAndGuildId(userId, guildId).orElseThrow(NotFoundDataException::new);
    }

    @Override
    public List<UserRead> findUserReadsByUserId(long userId) {
        return userReadQueryRepository.findUserReadsByUserId(userId);
    }
}

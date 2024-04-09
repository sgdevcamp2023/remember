package harmony.communityservice.user.service.query.impl;

import harmony.communityservice.common.dto.SearchUserReadRequest;
import harmony.communityservice.common.dto.VerifyGuildMemberRequest;
import harmony.communityservice.common.exception.NotFoundDataException;
import harmony.communityservice.user.domain.UserRead;
import harmony.communityservice.user.repository.query.UserReadQueryRepository;
import harmony.communityservice.user.service.query.UserReadQueryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserReadQueryServiceImpl implements UserReadQueryService {
    private final UserReadQueryRepository userReadQueryRepository;

    @Override
    public void existsByUserIdAndGuildId(VerifyGuildMemberRequest verifyGuildMemberRequest) {
        if (!userReadQueryRepository.existByUserIdAndGuildId(verifyGuildMemberRequest.userId(),
                verifyGuildMemberRequest.guildId())) {
            throw new NotFoundDataException();
        }
    }

    @Override
    public UserRead searchByUserIdAndGuildId(SearchUserReadRequest searchUserReadRequest) {
        return userReadQueryRepository.findByUserIdAndGuildId(searchUserReadRequest.userId(),
                searchUserReadRequest.guildId()).orElseThrow(NotFoundDataException::new);
    }

    @Override
    public List<UserRead> searchListByUserId(long userId) {
        return userReadQueryRepository.findUserReadsByUserId(userId);
    }

    @Override
    public List<UserRead> searchListByGuildId(long guildId) {
        return userReadQueryRepository.findUserReadsByGuildId(guildId);
    }


}
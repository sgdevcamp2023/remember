package harmony.communityservice.user.service.query.impl;

import harmony.communityservice.common.dto.SearchUserReadRequest;
import harmony.communityservice.common.dto.VerifyGuildMemberRequest;
import harmony.communityservice.common.exception.NotFoundDataException;
import harmony.communityservice.guild.guild.domain.GuildId;
import harmony.communityservice.user.domain.UserId;
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
        if (!userReadQueryRepository.existByUserIdAndGuildId(UserId.make(verifyGuildMemberRequest.userId()),
                GuildId.make(verifyGuildMemberRequest.guildId()))) {

            throw new NotFoundDataException();
        }
    }

    @Override
    public UserRead searchByUserIdAndGuildId(SearchUserReadRequest searchUserReadRequest) {
        return userReadQueryRepository.findByUserIdAndGuildId(UserId.make(searchUserReadRequest.userId()),
                GuildId.make(searchUserReadRequest.guildId())).orElseThrow(NotFoundDataException::new);
    }

    @Override
    public List<UserRead> searchListByUserId(long userId) {
        return userReadQueryRepository.findUserReadsByUserId(UserId.make(userId));
    }

    @Override
    public List<UserRead> searchListByGuildId(long guildId) {
        return userReadQueryRepository.findUserReadsByGuildId(GuildId.make(guildId));
    }


}
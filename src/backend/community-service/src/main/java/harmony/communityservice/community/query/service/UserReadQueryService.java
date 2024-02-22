package harmony.communityservice.community.query.service;

import harmony.communityservice.community.domain.UserRead;
import harmony.communityservice.community.query.dto.UserStatesResponseDto;
import java.util.List;
import java.util.Map;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface UserReadQueryService {
    void existsUserIdAndGuildId(long userId, long guildId);

    UserRead findUserReadIdAndGuildId(long userId, long guildId);

    List<UserRead> findUserReadsByUserId(long userId);

    UserStatesResponseDto findUserStatus(long guildId, long userId);
}

package harmony.communityservice.community.query.service;

import harmony.communityservice.community.domain.UserRead;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface UserReadQueryService {
    void existsUserIdAndGuildId(long userId, long guildId);

    UserRead findUserReadIdAndGuildId(long userId, long guildId);

    List<UserRead> findUserReadsByUserId(long userId);
}

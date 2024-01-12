package harmony.communityservice.community.query.repository;

import harmony.communityservice.community.domain.UserRead;
import java.util.List;
import java.util.Optional;

public interface UserReadQueryRepository {
    boolean existByUserIdAndGuildId(Long userid, Long guildId);

    Optional<UserRead> findByUserIdAndGuildId(Long userId, Long guildId);

    List<UserRead> findUserReadsByUserId(Long userId);
}

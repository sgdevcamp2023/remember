package harmony.communityservice.user.repository.command;

import harmony.communityservice.user.domain.UserRead;
import java.util.List;
import java.util.Optional;

public interface UserReadCommandRepository {

    void save(UserRead userRead);

    Optional<UserRead> findByUserIdAndGuildId(Long userId, Long guildId);

    List<UserRead> findListByUserId(Long userId);
}

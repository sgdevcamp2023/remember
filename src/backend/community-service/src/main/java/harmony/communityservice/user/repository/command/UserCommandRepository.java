package harmony.communityservice.user.repository.command;

import harmony.communityservice.user.domain.User;
import java.util.Optional;

public interface UserCommandRepository {

    void save(User user);

    Optional<User> findById(Long userId);
}

package harmony.communityservice.user.repository.command;

import harmony.communityservice.user.domain.User;

public interface UserCommandRepository {

    void save(User user);
}

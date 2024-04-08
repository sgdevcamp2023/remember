package harmony.communityservice.user.repository.command;

import harmony.communityservice.user.domain.UserRead;

public interface UserReadCommandRepository {

    void save(UserRead userRead);
}

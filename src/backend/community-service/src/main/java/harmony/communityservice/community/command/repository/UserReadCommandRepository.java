package harmony.communityservice.community.command.repository;

import harmony.communityservice.community.domain.UserRead;

public interface UserReadCommandRepository {

    void save(UserRead userRead);
}

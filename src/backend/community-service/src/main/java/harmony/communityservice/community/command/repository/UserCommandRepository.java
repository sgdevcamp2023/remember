package harmony.communityservice.community.command.repository;

import harmony.communityservice.community.domain.User;

public interface UserCommandRepository {

    void save(User user);
}

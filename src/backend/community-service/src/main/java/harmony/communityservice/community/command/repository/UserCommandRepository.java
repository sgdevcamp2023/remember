package harmony.communityservice.community.command.repository;

import harmony.communityservice.community.command.domain.User;

public interface UserCommandRepository {

    void save(User user);
}

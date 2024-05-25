package harmony.communityservice.user.application.port.in;

import harmony.communityservice.user.domain.User;

public interface LoadUserQuery {

    User loadUser(Long userId);
}

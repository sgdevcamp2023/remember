package harmony.communityservice.user.application.port.out;

import harmony.communityservice.user.domain.User;

public interface LoadUserCommandPort {
    User loadUser(Long userId);
}

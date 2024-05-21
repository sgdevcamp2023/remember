package harmony.communityservice.user.application.port.out;

import harmony.communityservice.user.domain.User;

public interface LoadUserQueryPort {

    User loadUser(Long userId);
}

package harmony.communityservice.user.application.port.in;

import harmony.communityservice.user.domain.User;

public interface LoadUserUseCase {
    User loadUser(Long userId);
}

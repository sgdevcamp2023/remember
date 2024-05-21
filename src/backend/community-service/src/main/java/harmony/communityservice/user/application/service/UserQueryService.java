package harmony.communityservice.user.application.service;

import harmony.communityservice.common.annotation.UseCase;
import harmony.communityservice.user.application.port.in.LoadUserUseCase;
import harmony.communityservice.user.application.port.out.LoadUserQueryPort;
import harmony.communityservice.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional(readOnly = true)
class UserQueryService implements LoadUserUseCase {

    private final LoadUserQueryPort loadUserPort;

    @Override
    public User loadUser(Long userId) {
        return loadUserPort.loadUser(userId);
    }
}

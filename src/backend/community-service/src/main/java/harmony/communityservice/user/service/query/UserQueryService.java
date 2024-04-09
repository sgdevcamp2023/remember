package harmony.communityservice.user.service.query;

import harmony.communityservice.user.domain.User;

public interface UserQueryService {
    User searchByUserId(Long userId);
}

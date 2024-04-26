package harmony.communityservice.user.service.query;

import harmony.communityservice.user.domain.User;
import harmony.communityservice.user.domain.UserId;

public interface UserQueryService {
    User searchByUserId(UserId userId);
}

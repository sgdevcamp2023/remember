package harmony.communityservice.community.query.service;

import harmony.communityservice.community.domain.User;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface UserQueryService {

    User findUser(Long userId);
}

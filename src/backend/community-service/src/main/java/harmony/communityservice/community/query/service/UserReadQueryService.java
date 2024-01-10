package harmony.communityservice.community.query.service;

import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface UserReadQueryService {
    void existsUserIdAndGuildId(long userId, long guildId);
}

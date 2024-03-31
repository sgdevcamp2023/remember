package harmony.communityservice.community.query.service;

import harmony.communityservice.common.dto.SearchUserReadRequest;
import harmony.communityservice.common.dto.VerifyGuildMemberRequest;
import harmony.communityservice.community.domain.UserRead;
import harmony.communityservice.community.query.dto.SearchUserStatesInGuildResponse;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface UserReadQueryService {

    void existsByUserIdAndGuildId(VerifyGuildMemberRequest verifyGuildMemberRequest);

    UserRead searchByUserIdAndGuildId(SearchUserReadRequest searchUserReadRequest);

    List<UserRead> searchListByUserId(long userId);

    SearchUserStatesInGuildResponse searchUserStatesInGuild(long guildId, long userId);
}

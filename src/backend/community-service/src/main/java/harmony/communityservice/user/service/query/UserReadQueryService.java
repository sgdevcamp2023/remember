package harmony.communityservice.user.service.query;

import harmony.communityservice.common.dto.SearchUserReadRequest;
import harmony.communityservice.common.dto.VerifyGuildMemberRequest;
import harmony.communityservice.user.domain.UserRead;
import java.util.List;

public interface UserReadQueryService {

    void existsByUserIdAndGuildId(VerifyGuildMemberRequest verifyGuildMemberRequest);

    UserRead searchByUserIdAndGuildId(SearchUserReadRequest searchUserReadRequest);

    List<UserRead> searchListByUserId(long userId);

    List<UserRead> searchListByGuildId(long guildId);
}

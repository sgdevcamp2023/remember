package harmony.communityservice.user.service.query;

import harmony.communityservice.common.dto.SearchUserReadRequest;
import harmony.communityservice.common.dto.VerifyGuildMemberRequest;
import harmony.communityservice.user.adapter.out.persistence.UserReadEntity;
import java.util.List;

public interface UserReadQueryService {

    void existsByUserIdAndGuildId(VerifyGuildMemberRequest verifyGuildMemberRequest);

    UserReadEntity searchByUserIdAndGuildId(SearchUserReadRequest searchUserReadRequest);

    List<UserReadEntity> searchListByUserId(long userId);

    List<UserReadEntity> searchListByGuildId(long guildId);
}

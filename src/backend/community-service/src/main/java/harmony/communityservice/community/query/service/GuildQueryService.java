package harmony.communityservice.community.query.service;

import harmony.communityservice.community.query.dto.SearchGuildInvitationCodeRequest;
import harmony.communityservice.community.domain.Guild;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface GuildQueryService {


    String searchInvitationCode(SearchGuildInvitationCodeRequest searchGuildInvitationCodeRequest);

    Guild searchByInvitationCode(String code);

    Guild searchByGuildId(Long guildId);

    boolean existsByGuildIdAndManagerId(Long guildId, Long managerId);
}

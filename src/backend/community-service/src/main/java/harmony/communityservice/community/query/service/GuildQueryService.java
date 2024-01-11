package harmony.communityservice.community.query.service;

import harmony.communityservice.community.command.dto.InvitationRequestDto;
import harmony.communityservice.community.domain.Guild;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface GuildQueryService {

    String findInviteCode(InvitationRequestDto requestDto);

    Guild findGuildByInviteCode(String code);

    Guild findByGuildId(Long guildId);

    boolean existsGuildByGuildIdAndManagerId(Long guildId, Long managerId);
}

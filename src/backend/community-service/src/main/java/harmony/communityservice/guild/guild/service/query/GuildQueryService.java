package harmony.communityservice.guild.guild.service.query;

import harmony.communityservice.common.dto.SearchParameterMapperRequest;
import harmony.communityservice.guild.guild.dto.SearchUserStatesInGuildResponse;
import harmony.communityservice.guild.domain.Guild;
import harmony.communityservice.guild.guild.dto.SearchGuildInvitationCodeRequest;

public interface GuildQueryService {


    String searchInvitationCode(SearchGuildInvitationCodeRequest searchGuildInvitationCodeRequest);

    Guild searchByInvitationCode(String code);


    boolean existsByGuildIdAndManagerId(Long guildId, Long managerId);

    SearchUserStatesInGuildResponse searchUserStatesInGuild(SearchParameterMapperRequest searchParameterMapperRequest);

    Guild searchById(Long guildId);
}

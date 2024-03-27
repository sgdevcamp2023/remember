package harmony.communityservice.community.query.service.impl;

import harmony.communityservice.common.exception.NotFoundDataException;
import harmony.communityservice.community.domain.Guild;
import harmony.communityservice.community.mapper.ToInvitationCodeMapper;
import harmony.communityservice.community.query.dto.SearchGuildInvitationCodeRequest;
import harmony.communityservice.community.query.repository.GuildQueryRepository;
import harmony.communityservice.community.query.service.GuildQueryService;
import harmony.communityservice.community.query.service.UserReadQueryService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GuildQueryServiceImpl implements GuildQueryService {

    private final GuildQueryRepository guildQueryRepository;
    private final UserReadQueryService userReadQueryService;

    @Override
    public String searchInvitationCode(SearchGuildInvitationCodeRequest searchGuildInvitationCodeRequest) {
        userReadQueryService.existsByUserIdAndGuildId(searchGuildInvitationCodeRequest.getUserId(),
                searchGuildInvitationCodeRequest.getGuildId());
        Guild guild = guildQueryRepository.findById(searchGuildInvitationCodeRequest.getGuildId())
                .orElseThrow(NotFoundDataException::new);

        return ToInvitationCodeMapper.convert(guild.getInviteCode(), searchGuildInvitationCodeRequest.getUserId(),
                searchGuildInvitationCodeRequest.getGuildId());
    }

    @Override
    public Guild searchByInvitationCode(String code) {
        return guildQueryRepository.findByInvitationCode(code).orElseThrow(NotFoundDataException::new);
    }

    @Override
    public Guild searchByGuildId(Long guildId) {
        return guildQueryRepository.findById(guildId).orElseThrow(NotFoundDataException::new);
    }

    @Override
    public boolean existsByGuildIdAndManagerId(Long guildId, Long managerId) {
        return guildQueryRepository.existsByGuildIdAndManagerId(guildId, managerId);
    }
}
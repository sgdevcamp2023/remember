package harmony.communityservice.community.query.service.impl;

import harmony.communityservice.common.dto.VerifyGuildMemberRequest;
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
        userReadQueryService.existsByUserIdAndGuildId(
                new VerifyGuildMemberRequest(searchGuildInvitationCodeRequest.userId(),
                        searchGuildInvitationCodeRequest.guildId()));
        Guild guild = guildQueryRepository.findById(searchGuildInvitationCodeRequest.guildId())
                .orElseThrow(NotFoundDataException::new);
        return ToInvitationCodeMapper.convert(guild.getInviteCode(), searchGuildInvitationCodeRequest.userId(),
                searchGuildInvitationCodeRequest.guildId());
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
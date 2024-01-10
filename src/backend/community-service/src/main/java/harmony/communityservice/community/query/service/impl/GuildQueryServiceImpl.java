package harmony.communityservice.community.query.service.impl;

import harmony.communityservice.community.command.dto.InvitationRequestDto;
import harmony.communityservice.community.domain.Guild;
import harmony.communityservice.community.mapper.ToInviteCodeMapper;
import harmony.communityservice.community.query.repository.GuildQueryRepository;
import harmony.communityservice.community.query.service.GuildQueryService;
import harmony.communityservice.community.query.service.UserReadQueryService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GuildQueryServiceImpl implements GuildQueryService {

    private final GuildQueryRepository guildQueryRepository;
    private final UserReadQueryService userReadQueryService;

    @Override
    public String findInviteCode(InvitationRequestDto requestDto) {
        userReadQueryService.existsUserIdAndGuildId(requestDto.getUserId(), requestDto.getGuildId());
        Guild guild = guildQueryRepository.findById(requestDto.getGuildId()).orElseThrow();

        return ToInviteCodeMapper.convert(guild.getInviteCode(), requestDto.getUserId());
    }

    @Override
    public Guild findGuildByInviteCode(String code) {
        return guildQueryRepository.findByInvitationCode(code).orElseThrow();
    }
}

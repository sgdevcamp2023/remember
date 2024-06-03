package harmony.communityservice.guild.guild.application.service;

import harmony.communityservice.common.annotation.UseCase;
import harmony.communityservice.guild.guild.application.port.in.LoadGuildIdsQuery;
import harmony.communityservice.guild.guild.application.port.in.LoadGuildReadCommand;
import harmony.communityservice.guild.guild.application.port.in.LoadGuildReadsQuery;
import harmony.communityservice.guild.guild.application.port.in.LoadUserGuildListQuery;
import harmony.communityservice.guild.guild.application.port.in.LoadVoiceUserQuery;
import harmony.communityservice.guild.guild.application.port.in.VerifyGuildMemberCommand;
import harmony.communityservice.guild.guild.application.port.in.VerifyGuildUserQuery;
import harmony.communityservice.guild.guild.application.port.out.LoadGuildIdsPort;
import harmony.communityservice.guild.guild.application.port.out.LoadGuildReadPort;
import harmony.communityservice.guild.guild.application.port.out.LoadGuildReadsPort;
import harmony.communityservice.guild.guild.application.port.out.VerifyGuildMemberPort;
import harmony.communityservice.guild.guild.domain.Guild.GuildId;
import harmony.communityservice.guild.guild.domain.GuildRead;
import harmony.communityservice.guild.guild.adapter.in.web.SearchGuildReadResponse;
import harmony.communityservice.user.domain.User.UserId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional(readOnly = true)
class GuildReadQueryService implements LoadGuildReadsQuery, LoadVoiceUserQuery, LoadUserGuildListQuery,
        VerifyGuildUserQuery, LoadGuildIdsQuery {

    private final LoadGuildReadsPort loadGuildReadsPort;
    private final LoadGuildReadPort loadGuildReadPort;
    private final VerifyGuildMemberPort verifyGuildMemberPort;
    private final LoadGuildIdsPort loadGuildIdsPort;

    @Override
    public Map<Long, SearchGuildReadResponse> loadGuilds(Long userId) {
        Map<Long, SearchGuildReadResponse> resultMap = new HashMap<>();
        for (GuildRead guildRead : loadGuildReadsPort.loadListByUserId(UserId.make(userId))) {
            resultMap.put(guildRead.getGuildId().getId(), LoadGuildReadResponseMapper.convert(guildRead));
        }
        return resultMap;
    }

    @Override
    public List<GuildId> loadGuildIdsByUserId(UserId userId) {
        return loadGuildIdsPort.loadList(userId);
    }

    @Override
    public void verify(VerifyGuildMemberCommand verifyGuildMemberCommand) {
        verifyGuildMemberPort.verify(UserId.make(verifyGuildMemberCommand.userId()),
                GuildId.make(verifyGuildMemberCommand.guildId()));
    }

    @Override
    public List<GuildRead> loadList(Long guildId) {
        return loadGuildReadsPort.loadListByGuildId(GuildId.make(guildId));
    }

    @Override
    public GuildRead loadByUserIdAndGuildId(LoadGuildReadCommand loadGuildReadCommand) {
        return loadGuildReadPort.loadByUserIdAndGuildId(UserId.make(loadGuildReadCommand.userId())
                , GuildId.make(loadGuildReadCommand.guildId()));
    }
}

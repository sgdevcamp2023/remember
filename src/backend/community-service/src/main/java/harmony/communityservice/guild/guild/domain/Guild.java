package harmony.communityservice.guild.guild.domain;

import harmony.communityservice.common.exception.NotFoundDataException;
import harmony.communityservice.domain.Domain;
import harmony.communityservice.domain.ValueObject;
import harmony.communityservice.guild.guild.domain.Guild.GuildId;
import harmony.communityservice.user.domain.User.UserId;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Guild extends Domain<Guild, GuildId> {

    private final String invitationCode;
    private final UserId managerId;
    private final ProfileInfo profileInfo;
    private final List<GuildUser> guildUsers = new ArrayList<>();
    private GuildId guildId;

    @Builder
    public Guild(GuildId guildId, String name, String profile, String inviteCode,
                 UserId managerId) {
        this.guildId = guildId;
        this.invitationCode = inviteCode;
        this.managerId = managerId;
        if (managerId != null) {
            guildUsers.add(GuildUser.make(managerId));
        }
        this.profileInfo = makeGuildInfo(name, profile);
    }

    private ProfileInfo makeGuildInfo(String name, String profile) {
        if (name == null || profile == null) {
            throw new NotFoundDataException();
        }

        return ProfileInfo.make(name, profile);
    }

    @Override
    public GuildId getId() {
        return guildId;
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class GuildId extends ValueObject<GuildId> {
        private final Long id;

        public static GuildId make(Long guildId) {
            return new GuildId(guildId);
        }

        @Override
        protected Object[] getEqualityFields() {
            return new Object[]{id};
        }
    }
}

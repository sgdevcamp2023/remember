package harmony.communityservice.guild.guild.domain;

import harmony.communityservice.domain.Domain;
import harmony.communityservice.guild.guild.domain.GuildUser.GuildUserId;
import harmony.communityservice.user.domain.User.UserId;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
public class GuildUser extends Domain<GuildUser, GuildUserId> {

    private GuildUserId guildUserId;

    private UserId userId;

    @Builder
    public GuildUser(GuildUserId guildUserId, UserId userId) {
        this.guildUserId = guildUserId;
        this.userId = userId;
    }

    private GuildUser(UserId userId) {
        this.userId = userId;
    }

    public static GuildUser make(UserId userId) {
        return new GuildUser(userId);
    }

    @Override
    public GuildUserId getId() {
        return guildUserId;
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class GuildUserId {
        private final Long id;

        public static GuildUserId make(Long guildUserId) {
            return new GuildUserId(guildUserId);
        }
    }
}


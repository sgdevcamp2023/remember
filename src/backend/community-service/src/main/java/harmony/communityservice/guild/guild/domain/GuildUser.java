package harmony.communityservice.guild.guild.domain;

import harmony.communityservice.user.domain.User.UserId;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
public class GuildUser {

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

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class GuildUserId {
        private final Long id;

        public static GuildUserId make(Long guildUserId) {
            return new GuildUserId(guildUserId);
        }
    }
}


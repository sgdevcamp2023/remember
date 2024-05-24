package harmony.communityservice.guild.guild.domain;

import harmony.communityservice.common.exception.NotFoundDataException;
import harmony.communityservice.common.exception.WrongThresholdRangeException;
import harmony.communityservice.domain.Domain;
import harmony.communityservice.domain.Threshold;
import harmony.communityservice.domain.ValueObject;
import harmony.communityservice.guild.guild.domain.GuildRead.GuildReadId;
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
        verifyGuildUserId(guildUserId);
        this.guildUserId = guildUserId;
        verifyUserId(userId);
        this.userId = userId;
    }

    private GuildUser(UserId userId) {
        this.userId = userId;
    }

    public static GuildUser make(UserId userId) {
        return new GuildUser(userId);
    }

    private void verifyUserId(UserId userId) {
        if (userId == null) {
            throw new NotFoundDataException("userId가 존재하지 않습니다");
        }

        if (userId.getId() < Threshold.MIN.getValue()) {
            throw new WrongThresholdRangeException("userId가 1 미만입니다");
        }
    }

    private void verifyGuildUserId(GuildUserId guildUserId) {
        if (guildUserId != null && guildUserId.getId() < Threshold.MIN.getValue()) {
            throw new WrongThresholdRangeException("guildReadId가 1 미만입니다");
        }
    }

    @Override
    public GuildUserId getId() {
        return guildUserId;
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class GuildUserId extends ValueObject<GuildUserId> {
        private final Long id;

        public static GuildUserId make(Long guildUserId) {
            return new GuildUserId(guildUserId);
        }

        @Override
        protected Object[] getEqualityFields() {
            return new Object[]{id};
        }
    }
}


package harmony.communityservice.guild.guild.domain;

import harmony.communityservice.common.exception.NotFoundDataException;
import harmony.communityservice.common.exception.WrongThresholdRangeException;
import harmony.communityservice.domain.Domain;
import harmony.communityservice.domain.Threshold;
import harmony.communityservice.domain.ValueObject;
import harmony.communityservice.guild.guild.domain.Guild.GuildId;
import harmony.communityservice.guild.guild.domain.GuildRead.GuildReadId;
import harmony.communityservice.user.domain.User.UserId;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
public class GuildRead extends Domain<GuildRead, GuildReadId> {

    private GuildReadId guildReadId;

    private GuildId guildId;

    private UserId userId;

    private ProfileInfo profileInfo;

    private CommonUserInfo commonUserInfo;

    @Builder
    public GuildRead(String userProfile, String userNickname, GuildId guildId, String name, String profile,
                     UserId userId, GuildReadId guildReadId) {
        this.commonUserInfo = makeUserInfo(userId, userNickname, userProfile);
        this.profileInfo = makeGuildInfo(guildId, name, profile);
        this.guildId = guildId;
        this.userId = userId;
        verifyGuildReadId(guildReadId);
        this.guildReadId = guildReadId;
    }

    private void verifyGuildReadId(GuildReadId guildReadId) {
        if (guildReadId != null && guildReadId.getId() < Threshold.MIN.getValue()) {
            throw new WrongThresholdRangeException("guildReadId가 1 미만입니다");
        }
    }

    private CommonUserInfo makeUserInfo(UserId userId, String name, String profile) {

        if (userId == null) {
            throw new NotFoundDataException("userId가 존재하지 않습니다");
        }

        if (userId.getId() < Threshold.MIN.getValue()) {
            throw new WrongThresholdRangeException("userId가 1 미만입니다");
        }

        if (name == null || profile == null) {
            throw new NotFoundDataException("유저의 Profile이나 Name 정보가 존재하지 않습니다");
        }

        return CommonUserInfo.make(name, profile);
    }

    private ProfileInfo makeGuildInfo(GuildId guildId, String name, String profile) {
        if (guildId == null) {
            throw new NotFoundDataException("guildId가 존재하지 않습니다");
        }

        if (guildId.getId() < Threshold.MIN.getValue()) {
            throw new WrongThresholdRangeException("guildId가 1 미만입니다");
        }

        if (name == null || profile == null) {
            throw new NotFoundDataException("길드의 Name이나 Profile 정보가 존재하지 않습니다");
        }

        return ProfileInfo.make(name, profile);
    }

    @Override
    public GuildReadId getId() {
        return guildReadId;
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class GuildReadId extends ValueObject<GuildReadId> {
        private Long id;

        public static GuildReadId make(Long id) {
            return new GuildReadId(id);
        }
    }
}

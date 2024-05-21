package harmony.communityservice.guild.guild.domain;

import harmony.communityservice.common.exception.NotFoundDataException;
import harmony.communityservice.guild.guild.domain.Guild.GuildId;
import harmony.communityservice.user.domain.User.UserId;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
public class GuildRead {

    private GuildReadId guildReadId;

    private GuildId guildId;

    private UserId userId;

    private ProfileInfo profileInfo;

    private CommonUserInfo commonUserInfo;

    @Builder
    public GuildRead(String userProfile, String userNickname, GuildId guildId, String name, String profile,
                     UserId userId, GuildReadId guildReadId) {
        this.commonUserInfo = makeUserInfo(userNickname, userProfile);
        this.guildId = guildId;
        this.profileInfo = makeGuildInfo(name, profile);
        this.userId = userId;
        this.guildReadId = guildReadId;
    }

    private CommonUserInfo makeUserInfo(String name, String profile) {
        if (name == null || profile == null) {
            throw new NotFoundDataException();
        }

        return CommonUserInfo.make(name, profile);
    }

    private ProfileInfo makeGuildInfo(String name, String profile) {
        if (name == null || profile == null) {
            throw new NotFoundDataException();
        }

        return ProfileInfo.make(name, profile);
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class GuildReadId {
        private Long id;

        public static GuildReadId make(Long id) {
            return new GuildReadId(id);
        }
    }
}

package harmony.communityservice.guild.guild.domain;

import harmony.communityservice.common.exception.NotFoundDataException;
import harmony.communityservice.common.exception.WrongThresholdRangeException;
import harmony.communityservice.domain.Domain;
import harmony.communityservice.domain.Threshold;
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
        verifyGuildId(guildId);
        this.guildId = guildId;
        verifyInvitationCode(inviteCode);
        this.invitationCode = inviteCode;
        verifyManagerId(managerId);
        this.managerId = managerId;
        if (guildUsers.isEmpty()) {
            guildUsers.add(GuildUser.make(managerId));
        }
        this.profileInfo = makeGuildInfo(name, profile);
    }

    private void verifyGuildId(GuildId guildId) {
        if (guildId != null && guildId.getId() < Threshold.MIN.getValue()) {
            throw new WrongThresholdRangeException("guildId가 1 미만입니다");
        }
    }

    private ProfileInfo makeGuildInfo(String name, String profile) {
        if (name == null || profile == null) {
            throw new NotFoundDataException();
        }

        return ProfileInfo.make(name, profile);
    }

    private void verifyInvitationCode(String inviteCode) {
        if (inviteCode == null) {
            throw new NotFoundDataException("데이터가 존재하지 않습니다");
        }
    }

    private void verifyManagerId(UserId managerId) {
        if (managerId == null) {
            throw new NotFoundDataException("데이터가 존재하지 않습니다");
        }

        if (managerId.getId() < Threshold.MIN.getValue()) {
            throw new WrongThresholdRangeException("managerId가 1 미만입니다.");
        }
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

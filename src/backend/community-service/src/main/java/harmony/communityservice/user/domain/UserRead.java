package harmony.communityservice.user.domain;

import harmony.communityservice.common.domain.AggregateRoot;
import harmony.communityservice.generic.CommonUserInfo;
import harmony.communityservice.guild.guild.domain.GuildId;
import harmony.communityservice.guild.guild.domain.GuildId.GuildIdJavaType;
import harmony.communityservice.user.domain.UserReadId.UserReadIdJavaType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JavaType;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user_read", indexes = @Index(name = "idx__guild_id__user_id", columnList = "guild_id, user_id"))
public class UserRead extends AggregateRoot<UserRead, UserReadId> {

    @Id
    @Column(name = "user_read_id")
    @JavaType(UserReadIdJavaType.class)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UserReadId userReadId;

    @Column(name = "guild_id")
    @JavaType(GuildIdJavaType.class)
    private GuildId guildId;

    @Embedded
    @Column(name = "user_id")
    private UserId userId;

    @Version
    private Long version;

    @Embedded
    private CommonUserInfo userInfo;

    @Builder
    public UserRead(GuildId guildId, UserId userId, String profile, String nickname) {
        this.guildId = guildId;
        this.userId = userId;
        this.userInfo = makeCommonUserInfo(profile, nickname);
    }

    private CommonUserInfo makeCommonUserInfo(String profile, String nickname) {
        return CommonUserInfo.make(nickname, profile);
    }

    public void modifyNickname(String nickname) {
        this.userInfo = userInfo.modifyNickname(nickname);
        super.updateType();
    }

    public void modifyProfile(String profile) {
        this.userInfo = userInfo.modifyProfile(profile);
        super.updateType();
    }

    @Override
    public UserReadId getId() {
        return userReadId;
    }

}

package harmony.communityservice.guild.guild.adapter.out.persistence;

import harmony.communityservice.common.domain.AggregateRoot;
import harmony.communityservice.common.generic.CommonUserInfoJpaVO;
import harmony.communityservice.common.generic.ProfileInfoJpaVO;
import harmony.communityservice.guild.guild.adapter.out.persistence.GuildIdJpaVO.GuildIdJavaType;
import harmony.communityservice.guild.guild.adapter.out.persistence.GuildReadIdJpaVO.GuildReadIdJavaType;
import harmony.communityservice.user.adapter.out.persistence.UserIdJpaVO;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JavaType;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "guild_read", indexes = @Index(name = "idx__userId__guildId", columnList = "user_id, guild_id"))
public class GuildReadEntity extends AggregateRoot<GuildReadEntity, GuildReadIdJpaVO> {

    @Id
    @Column(name = "guild_read_id")
    @JavaType(GuildReadIdJavaType.class)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private GuildReadIdJpaVO guildReadId;

    @Column(name = "guild_id")
    @JavaType(GuildIdJavaType.class)
    private GuildIdJpaVO guildId;

    @Embedded
    @Column(name = "user_id")
    private UserIdJpaVO userId;

    @Embedded
    private CommonUserInfoJpaVO commonUserInfo;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "name", column = @Column(name = "guild_name")),
            @AttributeOverride(name = "profile", column = @Column(name = "guild_profile"))
    })
    private ProfileInfoJpaVO guildInfo;

    @Builder
    public GuildReadEntity(GuildIdJpaVO guildId, UserIdJpaVO userId, String name, String profile, String userNickname,
                           String userProfile) {
        this.guildId = guildId;
        this.userId = userId;
        this.guildInfo = makeGuildInfo(name, profile);
        this.commonUserInfo = makeUserInfo(userNickname, userProfile);
    }

    private ProfileInfoJpaVO makeGuildInfo(String name, String profile) {
        return ProfileInfoJpaVO.make(name, profile);
    }

    private CommonUserInfoJpaVO makeUserInfo(String nickname, String profile) {
        return CommonUserInfoJpaVO.make(nickname, profile);
    }

    @Override
    public GuildReadIdJpaVO getId() {
        return guildReadId;
    }
}

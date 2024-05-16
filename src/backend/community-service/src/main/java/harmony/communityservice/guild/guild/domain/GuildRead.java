package harmony.communityservice.guild.guild.domain;

import harmony.communityservice.generic.ProfileInfoJpaVO;
import harmony.communityservice.guild.guild.domain.GuildId.GuildIdJavaType;
import harmony.communityservice.guild.guild.domain.GuildReadId.GuildReadIdJavaType;
import harmony.communityservice.user.adapter.out.persistence.UserIdJpaVO;
import harmony.communityservice.user.adapter.out.persistence.UserInfoJpaVO;
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
@Table(name = "guild_read", indexes = @Index(name = "idx__userId", columnList = "user_id"))
public class GuildRead {

    @Id
    @Column(name = "guild_read_id")
    @JavaType(GuildReadIdJavaType.class)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private GuildReadId guildReadId;

    @Column(name = "guild_id")
    @JavaType(GuildIdJavaType.class)
    private GuildId guildId;

    @Embedded
    @Column(name = "user_id")
    private UserIdJpaVO userId;

    @Embedded
    private UserInfoJpaVO userInfoJpaVO;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "name", column = @Column(name = "guild_name")),
            @AttributeOverride(name = "profile", column = @Column(name = "guild_profile"))
    })
    private ProfileInfoJpaVO guildInfo;

    @Builder
    public GuildRead(GuildId guildId, UserIdJpaVO userId, String name, String profile) {
        this.guildId = guildId;
        this.userId = userId;
        this.guildInfo = makeGuildInfo(name, profile);
    }

    private ProfileInfoJpaVO makeGuildInfo(String name, String profile) {
        return ProfileInfoJpaVO.make(name, profile);
    }
}

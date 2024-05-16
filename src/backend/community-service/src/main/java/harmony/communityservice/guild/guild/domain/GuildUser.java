package harmony.communityservice.guild.guild.domain;

import harmony.communityservice.common.domain.DomainEntity;
import harmony.communityservice.guild.guild.domain.GuildUserId.GuildUserIdJavaType;
import harmony.communityservice.user.adapter.out.persistence.UserId;
import harmony.communityservice.user.adapter.out.persistence.UserIdJpaVO;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JavaType;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "guild_user", indexes = @Index(name = "idx__user_id", columnList = "user_id"))
public class GuildUser extends DomainEntity<GuildUser, GuildUserId> {

    @Id
    @Column(name = "guild_user_id")
    @JavaType(GuildUserIdJavaType.class)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private GuildUserId guildUserId;

    @NotNull
    @Embedded
    @Column(name = "user_id")
    private UserIdJpaVO userId;


    private GuildUser(UserIdJpaVO userId) {
        this.userId = userId;
    }

    public static GuildUser make(UserIdJpaVO userId) {
        return new GuildUser(userId);
    }

    @Override
    public GuildUserId getId() {
        return guildUserId;
    }

}

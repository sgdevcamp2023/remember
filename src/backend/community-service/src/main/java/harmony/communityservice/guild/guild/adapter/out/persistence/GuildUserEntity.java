package harmony.communityservice.guild.guild.adapter.out.persistence;

import harmony.communityservice.common.domainentity.DomainEntity;
import harmony.communityservice.guild.guild.adapter.out.persistence.GuildUserIdJpaVO.GuildUserIdJavaType;
import harmony.communityservice.user.adapter.out.persistence.UserIdJpaVO;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JavaType;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "guild_user", indexes = @Index(name = "idx__user_id", columnList = "user_id"))
class GuildUserEntity extends DomainEntity<GuildUserEntity, GuildUserIdJpaVO> {

    @Id
    @Column(name = "guild_user_id")
    @JavaType(GuildUserIdJavaType.class)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private GuildUserIdJpaVO guildUserId;

    @NotNull
    @Embedded
    @Column(name = "user_id")
    private UserIdJpaVO userId;


    private GuildUserEntity(UserIdJpaVO userId) {
        this.userId = userId;
    }

    public static GuildUserEntity make(UserIdJpaVO userId) {
        return new GuildUserEntity(userId);
    }

    @Override
    public GuildUserIdJpaVO getId() {
        return guildUserId;
    }

}

package harmony.communityservice.guild.guild.adapter.out.persistence;

import harmony.communityservice.common.domainentity.AggregateRootEntity;
import harmony.communityservice.common.generic.ProfileInfoJpaVO;
import harmony.communityservice.guild.guild.adapter.out.persistence.GuildIdJpaVO.GuildIdJavaType;
import harmony.communityservice.user.adapter.out.persistence.UserIdJpaVO;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JavaType;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "guild", indexes = @Index(name = "idx__invite_code", columnList = "invite_code"))
class GuildEntity extends AggregateRootEntity<GuildEntity, GuildIdJpaVO> {

    @Id
    @Column(name = "guild_id")
    @JavaType(GuildIdJavaType.class)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private GuildIdJpaVO guildId;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "name", column = @Column(name = "guild_name")),
            @AttributeOverride(name = "profile", column = @Column(name = "guild_profile"))
    })
    private ProfileInfoJpaVO guildInfo;

    @NotBlank
    @Column(name = "invite_code")
    private String inviteCode;

    @NotNull
    @Embedded
    @Column(name = "manager_id")
    private UserIdJpaVO managerId;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "guild_id", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private List<GuildUserEntity> guildUsers = new ArrayList<>();

    @Builder
    public GuildEntity(String name, String profile, String inviteCode,
                       UserIdJpaVO managerId, List<GuildUserEntity> guildUserEntities) {
        this.guildInfo = makeGuildInfo(name, profile);
        this.inviteCode = inviteCode;
        this.managerId = managerId;
        this.guildUsers = guildUserEntities;
    }

    public void updateUserIds(GuildUserEntity guildUser) {
        this.guildUsers.add(guildUser);
        super.updateType();
    }

    private ProfileInfoJpaVO makeGuildInfo(String name, String profile) {
        return ProfileInfoJpaVO.make(name, profile);
    }

    @Override
    public GuildIdJpaVO getId() {
        return guildId;
    }
}

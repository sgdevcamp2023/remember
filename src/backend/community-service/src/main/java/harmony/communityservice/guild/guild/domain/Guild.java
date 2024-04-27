package harmony.communityservice.guild.guild.domain;

import harmony.communityservice.common.domain.AggregateRoot;
import harmony.communityservice.generic.CreationTime;
import harmony.communityservice.generic.ProfileInfo;
import harmony.communityservice.guild.guild.domain.GuildId.GuildIdJavaType;
import harmony.communityservice.user.domain.UserId;
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
@Table(name = "guild", indexes = @Index(name = "idx__invite_code", columnList = "invite_code"))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Guild extends AggregateRoot<Guild, GuildId> {

    @Id
    @Column(name = "guild_id")
    @JavaType(GuildIdJavaType.class)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private GuildId guildId;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "name", column = @Column(name = "guild_name")),
            @AttributeOverride(name = "profile", column = @Column(name = "guild_profile"))
    })
    private ProfileInfo guildInfo;

    @Embedded
    private CreationTime creationTime;

    @NotBlank
    @Column(name = "invite_code")
    private String inviteCode;

    @NotNull
    @Embedded
    @Column(name = "manager_id")
    private UserId managerId;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "guild_id", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private List<GuildUser> guildUsers = new ArrayList<>();

    @Builder
    public Guild(String name, String profile, String inviteCode,
                 UserId managerId) {
        this.guildInfo = makeGuildInfo(name, profile);
        this.creationTime = new CreationTime();
        this.inviteCode = inviteCode;
        this.managerId = managerId;
        this.guildUsers = new ArrayList<>();
    }

    public void updateUserIds(GuildUser guildUser) {
        this.guildUsers.add(guildUser);
    }

    private ProfileInfo makeGuildInfo(String name, String profile) {
        return ProfileInfo.make(name, profile);
    }

    @Override
    public GuildId getId() {
        return guildId;
    }
}

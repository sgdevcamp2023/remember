package harmony.communityservice.community.domain;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "guild")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Guild {

    @Id
    @Column(name = "guild_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long guildId;

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

    @Column(name = "manager_id")
    private Long managerId;

    @ElementCollection
    @CollectionTable(name = "guild_user",
            joinColumns = @JoinColumn(name = "guild_id"))
    private Set<Long> userIds = new HashSet<>();

    @Builder
    public Guild(String name, String profile, String inviteCode,
                 Long managerId, Long userId) {
        this.guildInfo = makeGuildInfo(name, profile);
        this.creationTime = new CreationTime();
        this.inviteCode = inviteCode;
        this.managerId = managerId;
        this.userIds = updateUserIds(userId);
    }

    public Set<Long> updateUserIds(long userId) {
        this.userIds = this.userIds == null ? new HashSet<>() : this.userIds;
        Set<Long> newUserIds = new HashSet<>(this.userIds);
        newUserIds.add(userId);
        return newUserIds;
    }

    private ProfileInfo makeGuildInfo(String name, String profile) {
        return ProfileInfo.make(name, profile);
    }
}

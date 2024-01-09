package harmony.communityservice.community.command.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Table(name = "guild")
public class Guild {

    @Id
    @Column(name = "guild_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long guildId;

    @NotBlank
    private String name;

    private String profile;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @NotBlank
    @Column(name = "invite_code")
    private String inviteCode;

    @Column(name = "manager_id")
    private Long managerId;

    @OneToMany(mappedBy = "guild")
    private List<GuildUser> guildUsers;


    @Builder
    public Guild(String name, String profile, String inviteCode,
                 Long managerId) {
        this.name = name;
        this.profile = profile;
        this.createdAt = LocalDateTime.now();
        this.inviteCode = inviteCode;
        this.managerId = managerId;
    }
}

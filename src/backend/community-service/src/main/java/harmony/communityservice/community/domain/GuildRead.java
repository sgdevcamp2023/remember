package harmony.communityservice.community.domain;

import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Table(name = "guild_read")
public class GuildRead {

    @Id
    @Column(name = "guild_read_id")
    private Long guildReadId;

    @Column(name = "user_id")
    private Long userId;

    @NotBlank
    private String name;

    @Nullable
    private String profile;

    @Builder
    public GuildRead(Long guildReadId, Long userId, String name, String profile) {
        this.guildReadId = guildReadId;
        this.userId = userId;
        this.name = name;
        this.profile = profile;
    }
}

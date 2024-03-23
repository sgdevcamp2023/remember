package harmony.communityservice.community.domain;

import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "guild_read", indexes = @Index(name = "idx__userId",columnList = "user_id"))
public class GuildRead {

    @Id
    @Column(name = "guild_read_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long guildReadId;

    @Column(name = "guild_id")
    private Long guildId;

    @Column(name = "user_id")
    private Long userId;

    @NotBlank
    private String name;

    @Nullable
    private String profile;

    @Builder
    public GuildRead(Long guildId, Long userId, String name, String profile) {
        this.guildId = guildId;
        this.userId = userId;
        this.name = name;
        this.profile = profile;
    }
}

package harmony.communityservice.community.domain;

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

@Entity
@Getter
@NoArgsConstructor
@Table(name = "guild_read", indexes = @Index(name = "idx__userId", columnList = "user_id"))
public class GuildRead {

    @Id
    @Column(name = "guild_read_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long guildReadId;

    @Column(name = "guild_id")
    private Long guildId;

    @Column(name = "user_id")
    private Long userId;

    @Embedded
    private GuildInfo guildInfo;

    @Builder
    public GuildRead(Long guildId, Long userId, String name, String profile) {
        this.guildId = guildId;
        this.userId = userId;
        this.guildInfo = makeGuildInfo(name, profile);
    }

    private GuildInfo makeGuildInfo(String name, String profile) {
        return GuildInfo.make(name, profile);
    }
}

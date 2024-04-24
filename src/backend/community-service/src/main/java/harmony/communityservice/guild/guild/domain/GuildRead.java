package harmony.communityservice.guild.guild.domain;

import harmony.communityservice.board.domain.ProfileInfo;
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
    @AttributeOverrides({
            @AttributeOverride(name = "name", column = @Column(name = "guild_name")),
            @AttributeOverride(name = "profile", column = @Column(name = "guild_profile"))
    })
    private ProfileInfo guildInfo;

    @Builder
    public GuildRead(Long guildId, Long userId, String name, String profile) {
        this.guildId = guildId;
        this.userId = userId;
        this.guildInfo = makeGuildInfo(name, profile);
    }

    private ProfileInfo makeGuildInfo(String name, String profile) {
        return ProfileInfo.make(name, profile);
    }
}

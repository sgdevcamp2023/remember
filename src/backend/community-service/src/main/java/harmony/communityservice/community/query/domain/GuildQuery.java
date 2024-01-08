package harmony.communityservice.community.query.domain;

import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Table(name = "guild_query")
public class GuildQuery {

    @Id
    @Column(name = "guild_query_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long guildQueryId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "guild_id")
    private Long guildId;

    @NotBlank
    private String name;

    @Nullable
    private String profile;
}

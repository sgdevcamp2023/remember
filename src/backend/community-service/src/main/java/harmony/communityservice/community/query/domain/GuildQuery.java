package harmony.communityservice.community.query.domain;

import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
    private Long guildQueryId;

    @Column(name = "user_id")
    private Long userId;

    @NotBlank
    private String name;

    @Nullable
    private String profile;
}

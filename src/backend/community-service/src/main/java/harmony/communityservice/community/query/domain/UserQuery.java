package harmony.communityservice.community.query.domain;

import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Table(name = "user_query")
public class UserQuery {

    @Id
    @Column(name = "user_query_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userQueryId;

    @NotNull
    @Column(name = "guild_id")
    private Long guildId;

    @NotNull
    @Column(name = "user_id")
    private Long UserId;

    @Nullable
    private String profile;

    @NotBlank
    private String nickname;
}

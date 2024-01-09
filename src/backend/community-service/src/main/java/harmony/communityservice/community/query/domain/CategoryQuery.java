package harmony.communityservice.community.query.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "category_query")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CategoryQuery {

    @Id
    @Column(name = "category_query_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryQueryId;

    @NotNull
    @Column(name = "guild_id")
    private Long guildId;

    @NotBlank
    @Column(name = "category_name")
    private String name;

}

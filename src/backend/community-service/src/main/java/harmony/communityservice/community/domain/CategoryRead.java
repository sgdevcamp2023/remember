package harmony.communityservice.community.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "category_read")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CategoryRead {

    @Id
    @Column(name = "category_read_id")
    private Long categoryReadId;

    @NotNull
    @Column(name = "guild_id")
    private Long guildId;

    @NotBlank
    @Column(name = "category_name")
    private String name;

    @Builder
    public CategoryRead(Long categoryReadId, Long guildId, String name) {
        this.categoryReadId = categoryReadId;
        this.guildId = guildId;
        this.name = name;
    }
}

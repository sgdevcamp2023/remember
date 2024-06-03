package harmony.communityservice.guild.category.adapter.out.persistence;

import harmony.communityservice.common.domainentity.AggregateRootEntity;
import harmony.communityservice.guild.category.adapter.out.persistence.CategoryIdJpaVO.CategoryIdJavaType;
import harmony.communityservice.guild.guild.adapter.out.persistence.GuildIdJpaVO;
import harmony.communityservice.guild.guild.adapter.out.persistence.GuildIdJpaVO.GuildIdJavaType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JavaType;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "category", indexes = @Index(name = "idx__guild_id", columnList = "guild_id"))
public class CategoryEntity extends AggregateRootEntity<CategoryEntity, CategoryIdJpaVO> {

    @Id
    @Column(name = "category_id")
    @JavaType(CategoryIdJavaType.class)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private CategoryIdJpaVO categoryId;

    @Column(name = "guild_id")
    @JavaType(GuildIdJavaType.class)
    private GuildIdJpaVO guildId;

    @NotBlank
    @Column(name = "category_name")
    private String name;

    @Builder
    public CategoryEntity(String name, GuildIdJpaVO guildId) {
        this.name = name;
        this.guildId = guildId;
    }

    @Override
    public CategoryIdJpaVO getId() {
        return categoryId;
    }
}

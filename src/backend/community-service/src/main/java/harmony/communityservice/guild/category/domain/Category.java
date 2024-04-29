package harmony.communityservice.guild.category.domain;

import harmony.communityservice.common.domain.AggregateRoot;
import harmony.communityservice.guild.category.domain.CategoryId.CategoryIdJavaType;
import harmony.communityservice.guild.guild.domain.GuildId;
import harmony.communityservice.guild.guild.domain.GuildId.GuildIdJavaType;
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
public class Category extends AggregateRoot<Category, CategoryId> {

    @Id
    @Column(name = "category_id")
    @JavaType(CategoryIdJavaType.class)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private CategoryId categoryId;

    @Column(name = "guild_id")
    @JavaType(GuildIdJavaType.class)
    private GuildId guildId;

    @NotBlank
    @Column(name = "category_name")
    private String name;

    @Builder
    public Category(String name, GuildId guildId) {
        this.name = name;
        this.guildId = guildId;
    }

    public void modifyName(String name) {
        this.name = name;
        super.updateType();
    }

    @Override
    public CategoryId getId() {
        return categoryId;
    }
}

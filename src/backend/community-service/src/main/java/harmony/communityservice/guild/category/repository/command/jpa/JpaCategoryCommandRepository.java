package harmony.communityservice.guild.category.repository.command.jpa;

import harmony.communityservice.guild.category.domain.Category;
import harmony.communityservice.guild.category.domain.CategoryId;
import harmony.communityservice.guild.guild.adapter.out.persistence.GuildIdJpaVO;
import jakarta.persistence.LockModeType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JpaCategoryCommandRepository extends JpaRepository<Category, CategoryId> {

    void deleteCategoryByCategoryId(CategoryId categoryId);

    @Modifying
    @Query("delete from Category c where c.guildId = :guildId")
    void deleteCategoriesByGuildId(@Param("guildId") GuildIdJpaVO guildId);

    @Lock(value = LockModeType.PESSIMISTIC_READ)
    Optional<Category> findCategoryByCategoryId(CategoryId categoryId);
}

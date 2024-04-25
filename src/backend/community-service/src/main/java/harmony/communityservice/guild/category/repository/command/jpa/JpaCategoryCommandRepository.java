package harmony.communityservice.guild.category.repository.command.jpa;

import harmony.communityservice.guild.category.domain.Category;
import jakarta.persistence.LockModeType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JpaCategoryCommandRepository extends JpaRepository<Category, Long> {

    void deleteCategoryByCategoryId(Long categoryId);

    @Modifying
    @Query("delete from Category c where c.guildId = :guildId")
    void deleteCategoriesByGuildId(@Param("guildId") Long guildId);

    @Lock(value = LockModeType.PESSIMISTIC_READ)
    Optional<Category> findById(Long categoryId);
}

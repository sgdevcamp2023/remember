package harmony.communityservice.guild.category.repository.command.jpa;

import harmony.communityservice.guild.category.domain.Category;
import jakarta.persistence.LockModeType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

public interface JpaCategoryCommandRepository extends JpaRepository<Category, Long> {

    void deleteCategoryByCategoryId(Long categoryId);

    void deleteCategoriesByGuildId(Long guildId);

    @Lock(value = LockModeType.PESSIMISTIC_READ)
    Optional<Category> findById(Long categoryId);
}

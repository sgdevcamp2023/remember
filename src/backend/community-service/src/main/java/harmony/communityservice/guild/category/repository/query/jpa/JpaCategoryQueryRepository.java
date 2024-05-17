package harmony.communityservice.guild.category.repository.query.jpa;

import harmony.communityservice.guild.category.domain.Category;
import harmony.communityservice.guild.category.domain.CategoryId;
import harmony.communityservice.guild.guild.adapter.out.persistence.GuildIdJpaVO;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JpaCategoryQueryRepository extends JpaRepository<Category, CategoryId> {

    @Query("select c from Category c where c.guildId = :guildId")
    List<Category> findCategoriesByGuildId(@Param("guildId") GuildIdJpaVO guildId);
}

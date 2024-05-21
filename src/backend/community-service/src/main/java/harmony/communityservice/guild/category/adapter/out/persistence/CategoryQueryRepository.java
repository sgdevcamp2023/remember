package harmony.communityservice.guild.category.adapter.out.persistence;

import harmony.communityservice.guild.guild.adapter.out.persistence.GuildIdJpaVO;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

interface CategoryQueryRepository extends JpaRepository<CategoryEntity, CategoryIdJpaVO> {

    @Query("select c from CategoryEntity c where c.guildId = :guildId")
    List<CategoryEntity> findCategoriesByGuildId(@Param("guildId") GuildIdJpaVO guildId);
}

package harmony.communityservice.guild.category.adapter.out.persistence;

import harmony.communityservice.guild.guild.adapter.out.persistence.GuildIdJpaVO;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

interface CategoryCommandRepository extends JpaRepository<CategoryEntity, CategoryIdJpaVO> {

    @Modifying
    @Query("delete from CategoryEntity c where c.guildId = :guildId")
    void deleteCategoriesByGuildId(@Param("guildId") GuildIdJpaVO guildId);

    @Modifying
    @Query("update CategoryEntity c set c.name = :newName where c.categoryId = :categoryId")
    void modifyCategory(@Param("newName") String newName, @Param("categoryId") CategoryIdJpaVO categoryId);
}

package harmony.communityservice.guild.category.adapter.out.persistence;

import static org.junit.jupiter.api.Assertions.*;

import harmony.communityservice.guild.category.domain.Category;
import harmony.communityservice.guild.guild.adapter.out.persistence.GuildIdJpaVO;
import harmony.communityservice.guild.guild.domain.Guild.GuildId;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
@EnableTransactionManagement
@Sql("CategoryQueryPersistenceAdapterTest.sql")
class CategoryQueryPersistenceAdapterTest {

    @Autowired
    CategoryQueryPersistenceAdapter categoryQueryPersistenceAdapter;

    @Autowired
    CategoryQueryRepository categoryQueryRepository;

    @Test
    @DisplayName("길드 내의 카테고리 조회 테스트")
    void load_categories() {
        List<Category> categories = categoryQueryPersistenceAdapter.loadList(GuildId.make(1L));

        List<CategoryEntity> categoryEntities = categoryQueryRepository.findCategoriesByGuildId(GuildIdJpaVO.make(1L));

        assertEquals(categories.size(),categoryEntities.size());
    }

}
package harmony.communityservice.guild.category.adapter.out.persistence;

import static org.junit.jupiter.api.Assertions.*;

import harmony.communityservice.common.exception.NotFoundDataException;
import harmony.communityservice.domain.Threshold;
import harmony.communityservice.guild.category.domain.Category;
import harmony.communityservice.guild.category.domain.Category.CategoryId;
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
@Sql("CategoryCommandPersistenceAdapterTest.sql")
class CategoryCommandPersistenceAdapterTest {

    @Autowired
    CategoryCommandPersistenceAdapter categoryCommandPersistenceAdapter;

    @Autowired
    CategoryCommandRepository categoryCommandRepository;

    @Test
    @DisplayName("카테고리 저장 테스트")
    void register_category() {
        Category category = Category.builder()
                .name("test_category")
                .guildId(GuildId.make(1L))
                .build();

        categoryCommandPersistenceAdapter.register(category);

        CategoryEntity categoryEntity = categoryCommandRepository.findById(CategoryIdJpaVO.make(7L))
                .orElseThrow(NotFoundDataException::new);

        assertEquals(category.getName(),categoryEntity.getName());
        assertEquals(category.getGuildId().getId(),categoryEntity.getGuildId().getId());
        assertEquals(categoryEntity.getId().getId(),7L);
    }

    @Test
    @DisplayName("카테고리 삭제 테스트")
    void delete_category() {
        categoryCommandPersistenceAdapter.delete(CategoryId.make(1L));

        List<CategoryEntity> categoryEntities = categoryCommandRepository.findAll();

        assertEquals(categoryEntities.size(),2L);
    }

    @Test
    @DisplayName("카테고리 수정 테스트")
    void modify_category() {
        categoryCommandPersistenceAdapter.modify(CategoryId.make(12L),"NEW_CATEGORY");

        CategoryEntity categoryEntity = categoryCommandRepository.findById(CategoryIdJpaVO.make(12L))
                .orElseThrow(NotFoundDataException::new);

        assertEquals(categoryEntity.getName(),"NEW_CATEGORY");
    }

    @Test
    @DisplayName("길드 내의 카테고리 삭제 테스트")
    void delete_categories() {
        categoryCommandPersistenceAdapter.deleteByGuildId(GuildId.make(1L));
        List<CategoryEntity> categoryEntities = categoryCommandRepository.findAll();

        assertEquals(categoryEntities.size(),0L);
    }


}
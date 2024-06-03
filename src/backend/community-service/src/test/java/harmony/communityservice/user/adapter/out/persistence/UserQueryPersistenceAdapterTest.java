package harmony.communityservice.user.adapter.out.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;

import harmony.communityservice.common.exception.NotFoundDataException;
import harmony.communityservice.user.domain.User;
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
class UserQueryPersistenceAdapterTest {

    @Autowired
    UserQueryPersistenceAdapter userQueryPersistenceAdapter;

    @Autowired
    UserCommandPersistenceAdapter userCommandPersistenceAdapter;

    @Autowired
    UserQueryRepository userQueryRepository;


    @Test
    @DisplayName("유저 조회 테스트")
    @Sql("UserQueryPersistenceAdapterTest.sql")
    void load_user() {
        User loadUser = userQueryPersistenceAdapter.loadUser(1L);
        UserEntity userEntity = userQueryRepository.findById(UserIdJpaVO.make(1L))
                .orElseThrow(NotFoundDataException::new);

        assertEquals(1L, loadUser.getUserId().getId());
        assertEquals(loadUser.getUserId().getId(), userEntity.getUserId().getId());
    }
}
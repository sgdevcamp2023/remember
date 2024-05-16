package harmony.communityservice.user.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserQueryRepository extends JpaRepository<UserJpaEntity, UserIdJpaVO> {
}

package harmony.communityservice.user.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCommandRepository extends JpaRepository<UserJpaEntity, UserIdJpaVO> {
}

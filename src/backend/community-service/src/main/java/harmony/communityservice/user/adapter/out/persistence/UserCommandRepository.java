package harmony.communityservice.user.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

interface UserCommandRepository extends JpaRepository<UserEntity, UserIdJpaVO> {
}

package harmony.communityservice.user.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

interface UserQueryRepository extends JpaRepository<UserEntity, UserIdJpaVO> {
}

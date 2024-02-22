package harmony.communityservice.community.query.repository.jpa;

import harmony.communityservice.community.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserQueryRepository extends JpaRepository<User, Long> {
}

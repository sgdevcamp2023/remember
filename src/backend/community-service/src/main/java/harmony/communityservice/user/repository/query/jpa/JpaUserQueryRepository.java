package harmony.communityservice.user.repository.query.jpa;

import harmony.communityservice.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserQueryRepository extends JpaRepository<User, Long> {
}

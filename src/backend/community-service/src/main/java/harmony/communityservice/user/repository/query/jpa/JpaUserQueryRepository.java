package harmony.communityservice.user.repository.query.jpa;

import harmony.communityservice.user.domain.User;
import harmony.communityservice.user.domain.UserId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserQueryRepository extends JpaRepository<User, UserId> {
}

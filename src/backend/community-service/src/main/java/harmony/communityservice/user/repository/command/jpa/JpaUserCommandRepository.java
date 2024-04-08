package harmony.communityservice.user.repository.command.jpa;

import harmony.communityservice.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserCommandRepository extends JpaRepository<User, Long> {
}

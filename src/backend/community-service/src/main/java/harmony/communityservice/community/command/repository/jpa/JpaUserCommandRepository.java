package harmony.communityservice.community.command.repository.jpa;

import harmony.communityservice.community.command.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserCommandRepository extends JpaRepository<User, Long> {
}

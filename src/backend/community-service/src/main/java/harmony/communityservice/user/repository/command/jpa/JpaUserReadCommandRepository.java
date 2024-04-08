package harmony.communityservice.user.repository.command.jpa;

import harmony.communityservice.user.domain.UserRead;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserReadCommandRepository extends JpaRepository<UserRead, Long> {
}

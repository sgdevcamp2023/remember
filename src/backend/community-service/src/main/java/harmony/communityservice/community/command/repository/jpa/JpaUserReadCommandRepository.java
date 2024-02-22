package harmony.communityservice.community.command.repository.jpa;

import harmony.communityservice.community.domain.UserRead;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserReadCommandRepository extends JpaRepository<UserRead, Long> {
}

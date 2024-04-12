package harmony.communityservice.user.repository.command.jpa;

import harmony.communityservice.user.domain.UserRead;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserReadCommandRepository extends JpaRepository<UserRead, Long> {

    Optional<UserRead> findByUserIdAndGuildId(Long userId, Long guildId);

    List<UserRead> findUserReadsByUserId(Long userId);
}

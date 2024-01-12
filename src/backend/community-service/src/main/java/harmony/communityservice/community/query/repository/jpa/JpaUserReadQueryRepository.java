package harmony.communityservice.community.query.repository.jpa;

import harmony.communityservice.community.domain.UserRead;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserReadQueryRepository extends JpaRepository<UserRead, Long> {
    boolean existsByGuildIdAndUserId(Long guildId, Long userId);

    Optional<UserRead> findByUserIdAndGuildId(Long userId, Long guildId);

    List<UserRead> findUserReadByUserId(Long userId);
}

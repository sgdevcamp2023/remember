package harmony.communityservice.community.query.repository.jpa;

import harmony.communityservice.community.domain.UserRead;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserReadQueryRepository extends JpaRepository<UserRead, Long> {
    boolean existsByGuildIdAndUserId(Long guildId, Long userId);
}
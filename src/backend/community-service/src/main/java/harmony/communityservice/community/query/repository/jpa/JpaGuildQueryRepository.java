package harmony.communityservice.community.query.repository.jpa;

import harmony.communityservice.community.domain.Guild;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaGuildQueryRepository extends JpaRepository<Guild,Long> {
    Optional<Guild> findByInviteCode(String code);
}

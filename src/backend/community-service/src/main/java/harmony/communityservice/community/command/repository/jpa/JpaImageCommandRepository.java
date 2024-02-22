package harmony.communityservice.community.command.repository.jpa;

import harmony.communityservice.community.domain.Image;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaImageCommandRepository extends JpaRepository<Image, Long> {
}

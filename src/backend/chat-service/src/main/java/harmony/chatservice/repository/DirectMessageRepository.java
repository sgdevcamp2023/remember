package harmony.chatservice.repository;

import harmony.chatservice.domain.DirectMessage;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DirectMessageRepository extends MongoRepository<DirectMessage, Long> {

    Long countByParentId(Long parentId);

    Optional<Page<DirectMessage>> findByRoomIdAndDelCheckFalse(Long roomId, Pageable pageable);

    Optional<Page<DirectMessage>> findByParentIdAndDelCheckFalse(Long parentId, Pageable pageable);
}
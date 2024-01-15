package harmony.chatservice.repository;

import harmony.chatservice.domain.DirectMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DirectMessageRepository extends MongoRepository<DirectMessage, Long> {

    Page<DirectMessage> findByRoomIdAndDelCheckFalse(Long roomId, Pageable pageable);
}
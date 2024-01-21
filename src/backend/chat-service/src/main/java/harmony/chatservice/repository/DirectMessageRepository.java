package harmony.chatservice.repository;

import harmony.chatservice.domain.DirectMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface DirectMessageRepository extends MongoRepository<DirectMessage, Long> {

    Long countByParentId(Long parentId);

    @Query("{ 'roomId': ?0, 'delCheck': false, 'parentId': 0 }")
    Page<DirectMessage> findByRoomIdAndDelCheckAndParentId(Long roomId, Pageable pageable);

    Page<DirectMessage> findByParentIdAndDelCheckFalse(Long parentId, Pageable pageable);
}
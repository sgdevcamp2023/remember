package harmony.chatservice.repository;

import harmony.chatservice.domain.DirectMessage;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface DirectMessageRepository extends MongoRepository<DirectMessage, Long> {

    @Query("{ 'roomId': ?0, 'delCheck': false, 'parentId': 0 }")
    Page<DirectMessage> findByRoomIdAndDelCheckAndParentId(Long roomId, Pageable pageable);

    Page<DirectMessage> findByParentIdAndDelCheckFalse(Long parentId, Pageable pageable);

    @Query(value = "{ 'parentId': { $in: ?0 } }")
    List<DirectMessage> countMessagesByParentIds(List<Long> parentIds);
}
package harmony.chatservice.repository;

import harmony.chatservice.domain.CommunityMessage;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunityMessageRepository extends MongoRepository<CommunityMessage, Long> {

    @Query("{ 'channelId': ?0, 'delCheck': false, 'parentId': 0 }")
    Page<CommunityMessage> findByChannelIdAndDelCheckAndParentId(Long channelId, Pageable pageable);

    Page<CommunityMessage> findByParentIdAndDelCheckFalse(Long parentId, Pageable pageable);

    @Query(value = "{ 'parentId': { $in: ?0 } }")
    List<CommunityMessage> countMessagesByParentIds(List<Long> parentIds);
}
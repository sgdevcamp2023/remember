package harmony.chatservice.repository;

import harmony.chatservice.domain.CommunityMessage;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunityMessageRepository extends MongoRepository<CommunityMessage, Long> {

    Long countByParentId(Long parentId);

    @Query("{ 'channelId': ?0, 'delCheck': false, 'parentId': 0 }")
    Optional<Page<CommunityMessage>> findByChannelIdAndDelCheckAndParentId(Long channelId, Pageable pageable);

    Optional<Page<CommunityMessage>> findByParentIdAndDelCheckFalse(Long parentId, Pageable pageable);
}
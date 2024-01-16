package harmony.chatservice.repository;

import harmony.chatservice.domain.CommunityMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunityMessageRepository extends MongoRepository<CommunityMessage, Long> {

    Page<CommunityMessage> findByChannelIdAndDelCheckFalse(Long channelId, Pageable pageable);

    Page<CommunityMessage> findByParentIdAndDelCheckFalse(Long parentId, Pageable pageable);
}
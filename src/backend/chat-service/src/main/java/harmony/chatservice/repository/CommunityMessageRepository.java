package harmony.chatservice.repository;

import harmony.chatservice.domain.CommunityMessage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunityMessageRepository extends MongoRepository<CommunityMessage, Long> {

}
package harmony.chatservice.repository;

import harmony.chatservice.domain.Emoji;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EmojiRepository extends MongoRepository<Emoji, Long> {

    List<Emoji> findAllByCommunityMessageId(Long communityMessageId);

    List<Emoji> findAllByDirectMessageId(Long directMessageId);
}
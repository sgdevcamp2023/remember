package harmony.chatservice.repository;

import harmony.chatservice.domain.Emoji;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface EmojiRepository extends MongoRepository<Emoji, Long> {

    @Query("{'directMessageId': {$in: ?0}}")
    List<Emoji> findEmojisByDirectMessageIds(List<Long> messageIds);

    @Query("{'communityMessageId': {$in: ?0}}")
    List<Emoji> findEmojisByCommunityMessageIds(List<Long> messageIds);
}